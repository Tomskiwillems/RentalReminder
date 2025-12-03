package RentalReminder.service;

import RentalReminder.entity.*;
import RentalReminder.entity.Currency;
import RentalReminder.exception.ApiException;
import RentalReminder.mapper.ContactMapper;
import RentalReminder.mapper.CurrencyMapper;
import RentalReminder.mapper.ItemMapper;
import RentalReminder.repository.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseGoodService<
        T,
        AddResponse,
        EditResponse,
        AddDataResponse,
        EditDataResponse,
        RequestDTO
        > extends BaseService {

    protected final UserProfileRepository userProfileRepository;
    protected final ContactRepository contactRepository;
    protected final ItemRepository itemRepository;
    protected final CurrencyRepository currencyRepository;
    protected final BorrowedGoodRepository borrowedGoodRepository;
    protected final LentGoodRepository lentGoodRepository;

    protected final AuthService authService;

    protected final ContactMapper contactMapper;
    protected final ItemMapper itemMapper;
    protected final CurrencyMapper currencyMapper;

    protected BaseGoodService(
            UserProfileRepository userProfileRepository,
            ContactRepository contactRepository,
            ItemRepository itemRepository,
            CurrencyRepository currencyRepository,
            BorrowedGoodRepository borrowedGoodRepository,
            LentGoodRepository lentGoodRepository,
            AuthService authService,
            ContactMapper contactMapper,
            ItemMapper itemMapper,
            CurrencyMapper currencyMapper
    ) {
        this.userProfileRepository = userProfileRepository;
        this.contactRepository = contactRepository;
        this.itemRepository = itemRepository;
        this.currencyRepository = currencyRepository;
        this.borrowedGoodRepository = borrowedGoodRepository;
        this.lentGoodRepository = lentGoodRepository;
        this.authService = authService;
        this.contactMapper = contactMapper;
        this.itemMapper = itemMapper;
        this.currencyMapper = currencyMapper;
    }

    protected UUID getCurrentUserId(HttpServletRequest request) {
        return super.getCurrentUserId(request, authService);
    }

    protected UserProfile getUserProfile(UUID userId) {
        return getEntityById(
                safeRepo(() -> userProfileRepository.findBySupabaseUserId(userId), "Failed to fetch user profile"),
                "User profile not found"
        );
    }

    /** Count usage from both borrowed and lent goods */
    private Map<Integer, Long> buildUsageCount(UUID userId) {

        Map<Integer, Long> usage = new HashMap<>();

        // Borrowed goods
        borrowedGoodRepository.findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userId)
                .forEach(bg -> {
                    if (bg.getContact() != null)
                        usage.merge(bg.getContact().getId(), 1L, Long::sum);
                    if (bg.getItem() != null)
                        usage.merge(bg.getItem().getId(), 1L, Long::sum);
                    if (bg.getCurrency() != null)
                        usage.merge(bg.getCurrency().getId(), 1L, Long::sum);
                });

        // Lent goods
        lentGoodRepository.findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userId)
                .forEach(lg -> {
                    if (lg.getContact() != null)
                        usage.merge(lg.getContact().getId(), 1L, Long::sum);
                    if (lg.getItem() != null)
                        usage.merge(lg.getItem().getId(), 1L, Long::sum);
                    if (lg.getCurrency() != null)
                        usage.merge(lg.getCurrency().getId(), 1L, Long::sum);
                });

        return usage;
    }

    /** Ordered contacts based on usage frequency */
    protected List<Contact> getOrderedContacts(UUID userId) {

        List<Contact> contacts = safeRepo(
                () -> contactRepository.findByUserProfileSupabaseUserId(userId),
                "Failed to fetch contacts"
        );

        Map<Integer, Long> usage = buildUsageCount(userId);

        return contacts.stream()
                .sorted(
                        Comparator
                                .comparing((Contact c) -> usage.getOrDefault(c.getId(), 0L)).reversed()
                                .thenComparing(Contact::getName)
                )
                .collect(Collectors.toList());
    }

    /** Ordered items based on usage frequency */
    protected List<Item> getOrderedItems(UUID userId) {

        List<Item> items = safeRepo(
                () -> itemRepository.findByUserProfileSupabaseUserId(userId),
                "Failed to fetch items"
        );

        Map<Integer, Long> usage = buildUsageCount(userId);

        return items.stream()
                .sorted(
                        Comparator
                                .comparing((Item i) -> usage.getOrDefault(i.getId(), 0L)).reversed()
                                .thenComparing(Item::getName)
                )
                .collect(Collectors.toList());
    }

    /** Ordered currencies based on usage frequency */
    protected List<Currency> getOrderedCurrencies(UUID userId) {

        List<Currency> currencies = safeRepo(
                () -> currencyRepository.findByUserProfileSupabaseUserIdOrUserProfileSupabaseUserIdIsNull(userId),
                "Failed to fetch currencies"
        );

        Map<Integer, Long> usage = buildUsageCount(userId);

        return currencies.stream()
                .sorted(
                        Comparator
                                .comparing((Currency c) -> usage.getOrDefault(c.getId(), 0L)).reversed()
                                .thenComparing(Currency::getName)
                )
                .collect(Collectors.toList());
    }

    protected Contact getContactOrThrow(int contactId, UUID currentUserId) {
        Contact contact = getEntityById(
                safeRepo(() -> contactRepository.findById(contactId), "Failed to fetch contact"),
                "Contact not found"
        );

        checkOwnership(
                contact.getUserProfile().getSupabaseUserId(),
                currentUserId,
                "Contact does not belong to this user"
        );

        return contact;
    }

    protected Item getItemOrThrow(Integer itemId, UUID currentUserId) {
        if (itemId == null) return null;

        Item item = getEntityById(
                safeRepo(() -> itemRepository.findById(itemId), "Failed to fetch item"),
                "Item not found"
        );

        checkOwnership(
                item.getUserProfile().getSupabaseUserId(),
                currentUserId,
                "Item does not belong to this user"
        );

        return item;
    }

    protected Currency getCurrencyOrThrow(Integer currencyId) {
        if (currencyId == null) return null;

        return getEntityById(
                safeRepo(() -> currencyRepository.findById(currencyId), "Failed to fetch currency"),
                "Currency not found"
        );
    }

    protected void validateItemOrCurrency(Integer itemId, Integer currencyId) {
        if ((itemId == null && currencyId == null) || (itemId != null && currencyId != null)) {
            throw new ApiException("Exactly one of Item or Currency must be set");
        }
    }

    /* Generic JPA optional wrapper */
    protected <E> E getEntityOrThrow(
            Supplier<Optional<E>> repoCall,
            String fetchErrorMsg,
            String notFoundMsg
    ) {
        return getEntityById(
                safeRepo(repoCall, fetchErrorMsg),
                notFoundMsg
        );
    }

    protected AddDataResponse getAddData(Supplier<AddDataResponse> createResponseSupplier) {
        return createResponseSupplier.get();
    }
}
