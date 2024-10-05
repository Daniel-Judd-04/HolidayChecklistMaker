package com.example;

// Formatting rules:
// 4 '____'   = ' / '   e.g. 'Jumper / Hoody'
// 3 '___'    = ' - '   e.g. 'Bra - Black'
// 2 '__'     = '-'     e.g. 'T-shirts'
// 1 '_'      = ' '     e.g. 'Smart Shoes'
// - '0'      = 'CAPS'  e.g. 'GHIC Card'

import java.util.Comparator;

public enum Item {
    // COMMON
    SOCKS(false, 1, 0.1f),
    PANTS(false, 1, 0.1f),
    SHORTS(false, 0.5f, 0.1f, new Tag[] {Tag.HOT, Tag.WARM}),
    T__SHIRTS(false, 1, 0.2f),

    SMART_SHOES(false, 0.15f, 0.03f, new Tag[] {Tag.SMART, Tag.SMART_CASUAL}),

    SHOES(false, 0.15f, 0.03f),
    TROUSERS(false, 0.35f, 0.03f),
    SWIMMING_COSTUME(false, 0.15f, 0.03f, new Tag[] {Tag.BEACH, Tag.POOL, Tag.HOT, Tag.SUNNY, Tag.BOAT}),
    JUMPER____HOODY(false, 0.3f, 0.03f, new Tag[] {Tag.COLD, Tag.MILD, Tag.WET, Tag.DAMP, Tag.CASUAL}),

    TRAINERS(new Tag[] {Tag.CASUAL, Tag.WET, Tag.HOT, Tag.DAMP, Tag.WARM, Tag.OUTDOORS}),
    SANDALS____SLIP__ONS(new Tag[] {Tag.DRY, Tag.OUTDOORS, Tag.BEACH, Tag.HOT, Tag.BEACH}),
    FLIP_FLOPS(new Tag[] {Tag.WARM, Tag.HOT, Tag.BEACH, Tag.POOL, Tag.SUNNY, Tag.OUTDOORS}),
    SLIPPERS(new Tag[] {Tag.UK0, Tag.COLD, Tag.MILD, Tag.CASUAL}, new ItemSet[] {ItemSet.LOUIE, ItemSet.DELLA}),
    COAT(new Tag[] {Tag.COLD, Tag.MILD, Tag.WET, Tag.DAMP, Tag.OUTDOORS}),
    JACKET(new Tag[] {Tag.COLD, Tag.MILD, Tag.WET, Tag.DAMP, Tag.MOIST}),
    HAT____CAP(new Tag[] {Tag.HOT, Tag.WARM, Tag.SUNNY}),
    WOOLLY_HAT(new Tag[] {Tag.COLD}),
    GLOVES(new Tag[] {Tag.COLD}),
    SCARF(new Tag[] {Tag.COLD}),
    LAYERS(new Tag[] {Tag.COLD}),
    UMBRELLA(new Tag[] {Tag.WET, Tag.DAMP}),
    HEADPHONES(),
    HEADPHONES_CHARGER(),
    PHONE(),
    PHONE_CHARGER(),
    IPAD(),
    IPAD_CHARGER(),
    PASSPORT(new Tag[] {Tag.ABROAD}),
    GHIC0_CARD(new Tag[] {Tag.EUROPE}),
    DRIVING_LICENSE(),
    WASH_BAG(),
    SHAMPOO(),
    CONDITIONER(),
    SHOWER_GEL(),
    BODY_WASH(),
    FACE_WASH(),
    DEODORANT(),
    TOOTHPASTE(),
    TOOTHBRUSH(),
    PILLOW(),
    KINDLE(),
    SUNGLASSES(new Tag[] {Tag.SUNNY, Tag.HOT, Tag.WARM, Tag.BEACH}),
    SWIM_TOWEL(true, 1, 0, new Tag[] {Tag.SUNNY, Tag.HOT, Tag.WARM, Tag.BEACH, Tag.POOL}, new Tag[] {Tag.UK0}), // ?


    // PERSONAL
    SEA_SICKNESS_MEDICATION(new Tag[] {Tag.BOAT, Tag.BEACH}, new ItemSet[] {ItemSet.DELLA}),
    COMB(new ItemSet[] {ItemSet.LOUIE, ItemSet.DANIEL}),
    GLASSES_CLEANER(new ItemSet[] {ItemSet.DANIEL, ItemSet.STUFF}),
    HAIR_BRUSH(new ItemSet[] {ItemSet.DELLA}),
    HAIRSPRAY(new ItemSet[] {ItemSet.DELLA}),
    HAIR_STUFF(new ItemSet[] {ItemSet.LOUIE, ItemSet.DANIEL}),
    HAIR_MOUSSE(new ItemSet[] {ItemSet.DELLA}),
    HAIR_BAND(new ItemSet[] {ItemSet.DELLA}),
    BODY_SPRAY(new ItemSet[] {ItemSet.DELLA}),
    EYE_DROPS(new ItemSet[] {ItemSet.NEAL}),
    TEETH_CLEANSING(false, 1, 0, new Tag[] {Tag.ALL}, 2, 0, new ItemSet[] {ItemSet.LOUIE, ItemSet.NEAL}),
    INVISALIGN(new ItemSet[]{ItemSet.LOUIE}),
    TEETH_GUARD(new ItemSet[] {ItemSet.NEAL}),
    NASAL_BLACKHEAD_STRIPS(new ItemSet[] {ItemSet.NEAL}),
    WATCH(new ItemSet[] {ItemSet.NEAL}),
    APPLE_WATCH(new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE, ItemSet.DANIEL}),
    APPLE_WATCH_CHARGER(new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE, ItemSet.DANIEL}),
    LAPTOP(new ItemSet[] {ItemSet.LOUIE, ItemSet.DELLA, ItemSet.DANIEL}),
    LAPTOP_CHARGER(new ItemSet[] {ItemSet.LOUIE, ItemSet.DELLA, ItemSet.DANIEL}),
    WATCH_BANDS(new ItemSet[] {ItemSet.DELLA}),
    RAZOR(new ItemSet[] {ItemSet.DELLA}),
    AFTER_SHAVE(new ItemSet[]{ItemSet.NEAL, ItemSet.DANIEL}),
    SHAVER(new ItemSet[] {ItemSet.NEAL, ItemSet.DANIEL}),
    EARPLUGS(new ItemSet[] {ItemSet.DELLA}), // ?
    GOGGLES(new Tag[] {Tag.BEACH, Tag.POOL, Tag.BOAT}, new ItemSet[] {ItemSet.DELLA, ItemSet.DANIEL, ItemSet.LOUIE}),
    RUCKSACK(new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE, ItemSet.DANIEL}), // **
    VITAMINS(new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE, ItemSet.NEAL}),
    CABI_JEWELRY(new ItemSet[] {ItemSet.DELLA}),
    NICE_JEWELRY(new Tag[] {Tag.SMART}, new ItemSet[] {ItemSet.DELLA}),
    TAPE_MEASURE(new ItemSet[] {ItemSet.DELLA}),
    SCALES(new ItemSet[] {ItemSet.DELLA}),
    NASAL_STEROID_SPRAY(new ItemSet[] {ItemSet.DELLA}),
    STERIMAR(new ItemSet[] {ItemSet.DELLA}),
    MASCARA(new ItemSet[] {ItemSet.DELLA}),
    TIGER_BALM(new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE, ItemSet.DANIEL}),
    TRINNY_MAKE__UP(new ItemSet[] {ItemSet.DELLA}),
    TRINNY_BRUSHES(new ItemSet[] {ItemSet.DELLA}),
    TRINNY_FACE_WASH(new ItemSet[] {ItemSet.DELLA}),
    TRINNY_MOISTURISER(new ItemSet[] {ItemSet.DELLA}),
    TAMPONS(new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),
    TWEEZERS(new ItemSet[] {ItemSet.DELLA}),
    SLEEP_GUMMIES(new ItemSet[] {ItemSet.DELLA}),
    HANDBAGS(new ItemSet[] {ItemSet.DELLA}),
    SLIPPERS_(new ItemSet[] {ItemSet.NEAL}),
    RICE_CRACKERS(new ItemSet[] {ItemSet.DELLA}),
    FLORA(new ItemSet[] {ItemSet.DELLA}),
    TREK_BARS(new ItemSet[] {ItemSet.DELLA}),
    COTTON_BUDS(new ItemSet[] {ItemSet.DELLA}),
    FLANNEL(new ItemSet[] {ItemSet.DELLA}),
    NIGHT_CREAM(new ItemSet[] {ItemSet.DELLA}),
    CHARCOAL(new ItemSet[] {ItemSet.DELLA}),
    BELT(new ItemSet[] {ItemSet.NEAL, ItemSet.DANIEL}), // ?
    NAIL_CLIPPERS(new ItemSet[] {ItemSet.NEAL, ItemSet.STUFF}), // ?
    ICE_PACK(new ItemSet[] {ItemSet.NEAL}),

    TRAVEL_DIARY(true, 1, 0, new Tag[] {Tag.ALL}, 3, 0, new ItemSet[] {ItemSet.DELLA}),
    PRESCRIPTION_SUNGLASSES(new Tag[] {Tag.SUNNY, Tag.HOT, Tag.WARM, Tag.BEACH}, new ItemSet[] {ItemSet.DANIEL}),

    TRACKSUITS(false, 0.3f, 0.03f, new ItemSet[] {ItemSet.DANIEL, ItemSet.LOUIE}),
    LEGGINGS(false, 0.3f, 0.03f, new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),

    JEANS(false, 0.2f, 0.01f, new Tag[] {Tag.COLD, Tag.MILD, Tag.WARM, Tag.SMART_CASUAL}, new ItemSet[] {ItemSet.DELLA, ItemSet.DANIEL, ItemSet.NEAL}), // ?
    PYJAMAS(false, 0.15f, 0.03f, new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),
    BOOKS(false, 0.15f, 0.01f, new ItemSet[] {ItemSet.LOUIE}),
    BRA___BLACK(false, 0.05f, 0, new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),
    BRA___NEUTRAL(false, 0.05f, 0, new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),
    BRA___STRAPLESS(false, 0.05f, 0, new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),
    BRA___WHITE(false, 0.05f, 0, new ItemSet[] {ItemSet.DELLA, ItemSet.LOUIE}),

    DRESS(false, 0.4f, 0.02f, new Tag[] {Tag.SMART, Tag.SMART_CASUAL}, new ItemSet[]{ItemSet.DELLA, ItemSet.LOUIE}),
    SMART_OUTFIT(false, 0.4f, 0.02f, new Tag[] {Tag.SMART, Tag.SMART_CASUAL}, new ItemSet[]{ItemSet.DELLA, ItemSet.LOUIE}),
    SMART_SHORTS(false, 0.4f, 0.03f, new Tag[] {Tag.HOT, Tag.WARM}, new Tag[] {Tag.SMART, Tag.SMART_CASUAL}),
    SMART_TROUSERS(false, 0.4f, 0.03f, new Tag[] {Tag.SMART, Tag.SMART_CASUAL}, new ItemSet[]{ItemSet.NEAL, ItemSet.DANIEL}),
    SMART_SHIRTS(false, 1, 0.03f, new Tag[] {Tag.SMART, Tag.SMART_CASUAL}, new ItemSet[]{ItemSet.NEAL, ItemSet.DANIEL}),
    SUIT(new Tag[] {Tag.SMART}, new ItemSet[]{ItemSet.NEAL, ItemSet.DANIEL}),
    TIE(new Tag[] {Tag.SMART}, new ItemSet[]{ItemSet.NEAL, ItemSet.DANIEL}),


    // JOBS
    BEDDING_OUT(new ItemSet[]{ItemSet.JOBS}),
    LOUIE___STRIP_BED(new ItemSet[]{ItemSet.JOBS, ItemSet.LOUIE}), // ?


    // STUFF
    DRINKS_BOTTLES(new ItemSet[]{ItemSet.STUFF}), // ? Water

    FIRST_AID_KIT(new ItemSet[]{ItemSet.STUFF}),
    TISSUES(new ItemSet[]{ItemSet.STUFF}),
    HOUSE_KEYS(new ItemSet[] {ItemSet.STUFF}),
    LIP_CREAM(new ItemSet[] {ItemSet.STUFF}),
    MARSHALL_SPEAKER(new ItemSet[] {ItemSet.STUFF}),

    SUN_TAN_CREAM(new Tag[] {Tag.HOT, Tag.WARM, Tag.SUNNY}, new ItemSet[] {ItemSet.STUFF}),
    BUG_SPRAY(new Tag[] {Tag.HOT, Tag.WARM, Tag.SUNNY, Tag.OUTDOORS}, new ItemSet[]{ItemSet.STUFF}),
    SOLAR_CHARGER(new Tag[] {Tag.OUTDOORS}, new ItemSet[]{ItemSet.STUFF}), // ?
    TOWELS(new Tag[] {Tag.OUTDOORS}, new ItemSet[]{ItemSet.STUFF}), // ?
    BLANKETS(new Tag[] {Tag.OUTDOORS, Tag.COLD}, new ItemSet[]{ItemSet.STUFF}),
    CHAIRS(new Tag[] {Tag.OUTDOORS}, new ItemSet[]{ItemSet.STUFF}),
    PLUG_ADAPTERS(new Tag[] {Tag.ABROAD, Tag.EUROPE}, new ItemSet[]{ItemSet.STUFF}),
    WISE_CARD(new Tag[] {Tag.ABROAD, Tag.EUROPE}, new ItemSet[]{ItemSet.STUFF}),

    GAS_STOVE(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    CANISTERS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    CUTLERY(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    KETTLE(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    COOL_BOX(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    ICE_PACKS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    TEA_BAGS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    COKE(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    BACON(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    AVOCADO(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    BREAD_ROLLS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    HUMMUS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    FALAFELS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    OATCAKES(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    PEANUT_BUTTER(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    CRACKERS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    FRUIT(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    SMOKED_SALMON(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    NUTS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    CRISPS(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    OLIVES(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    SNACK_BAR(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    ORANGE_JUICE(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    DASH(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    WATER(new Tag[] {Tag.SELF_CATERED}, new ItemSet[]{ItemSet.STUFF}),
    ;


    private final boolean constant;
    private final float minAmount;
    private final float variation;

    private final Tag[] tags;
    private final Tag[] requiredTags; // Only recommended if all required tags, and at least one regular tag (ALL can be used), are present in holiday tags.

    private final int requiredNights;
    private final int requiredParticipants;

    private final ItemSet[] appliesTo; // if null then apply to all participants


    Item () {
        // The default item. No requirements, with only 1 amount.
        // Only commonality is defined.
        this(true, 1, 0, new Tag[] {Tag.ALL}, new Tag[0], 0, 0, null);
    }

    Item (ItemSet[] appliesTo) {
        // The default item. No requirements, with only 1 amount.
        // Only commonality is defined.
        this(true, 1, 0, new Tag[] {Tag.ALL}, new Tag[0], 0, 0, appliesTo);
    }

    Item (Tag[] tags) {
        // Only 1 amount, with no additional requirements.
        // Commonality and tags are defined.
        this(true, 1, 0, tags, new Tag[0], 0, 0, null);
    }

    Item (Tag[] tags, ItemSet[] appliesTo) {
        // Only 1 amount, with no additional requirements.
        // Commonality and tags are defined.
        this(true, 1, 0, tags, new Tag[0], 0, 0, appliesTo);
    }

    Item (boolean constant, float minAmount, float variation) {
        // No tags or requiredTags and no minimum nights or participants.
        // Most likely has a variable constant.
        this(constant, minAmount, variation, new Tag[] {Tag.ALL}, new Tag[0], 0, 0, null);
    }

    Item (boolean constant, float minAmount, float variation, ItemSet[] appliesTo) {
        // No tags or requiredTags and no minimum nights or participants.
        // Most likely has a variable constant.
        this(constant, minAmount, variation, new Tag[] {Tag.ALL}, new Tag[0], 0, 0, appliesTo);
    }

    Item (boolean constant, float minAmount, float variation, Tag[] tags) {
        // No requiredTags and no minimum nights or participants.
        // Most likely has a variable constant.
        this(constant, minAmount, variation, tags, new Tag[0], 0, 0, null);
    }

    Item (boolean constant, float minAmount, float variation, Tag[] tags, ItemSet[] appliesTo) {
        // No requiredTags and no minimum nights or participants.
        // Most likely has a variable constant.
        this(constant, minAmount, variation, tags, new Tag[0], 0, 0, appliesTo);
    }

    Item (boolean constant, float minAmount, float variation, Tag[] tags, Tag[] requiredTags) {
        // Assume no minimum nights or participants.
        // The provided required tags mean that this item will only appear if:
        //  - all the required tags are met
        //  - and at least one normal tag is met
        this(constant, minAmount, variation, tags, requiredTags, 0, 0, null);
    }

    Item (boolean constant, float minAmount, float variation, Tag[] tags, int requiredNights, int requiredParticipants) {
        // Assume no requiredTags
        // The number of holiday nights and participants must be more or equal the given ints
        this(constant, minAmount, variation, tags, new Tag[0], requiredNights, requiredParticipants, null);
    }

    Item (boolean constant, float minAmount, float variation, Tag[] tags, int requiredNights, int requiredParticipants, ItemSet[] appliesTo) {
        // Assume no requiredTags
        // The number of holiday nights and participants must be more or equal the given ints
        this(constant, minAmount, variation, tags, new Tag[0], requiredNights, requiredParticipants, appliesTo);
    }

    /**
     * The default constructor for the @link{Item} object.
     * Creates an item that has customised properties, which determine how or if the item will be recommended for the holiday.
     *
     * @param constant              Whether the minAmount and variation increase with nights.
     * @param minAmount             The minimum number of this item to be recommended.
     * @param variation             The variation in the recommended number over time.
     *
     * @param tags                  At least one tag must appear in the holiday.
     * @param requiredTags          If given, at least one required tag must appear in the holiday.
     *
     * @param requiredNights        The length of the holiday must be more or equal to this.
     * @param requiredParticipants  The number of participants must be more or equal to this.
     *
     * @param appliesTo             The item sets that this item should be added to.
     */
    Item (boolean constant, float minAmount, float variation, Tag[] tags, Tag[] requiredTags, int requiredNights, int requiredParticipants, ItemSet[] appliesTo) {
        this.constant = constant;
        this.minAmount = minAmount;
        this.variation = variation;

        this.tags = tags;
        this.requiredTags = requiredTags;

        this.requiredNights = requiredNights;
        this.requiredParticipants = requiredParticipants;

        this.appliesTo = appliesTo;
    }

    private static final Comparator<Item> TAG_COMPARATOR = Comparator.comparingInt(item -> item.getLowestRelevantTag().ordinal());
    public static Comparator<Item> getTagComparator() {
        return TAG_COMPARATOR;
    }
    private static final Comparator<Item> NAME_COMPARATOR = Comparator.comparing(Item::getName);

    public static Comparator<Item> getNameComparator() {
        return NAME_COMPARATOR;
    }

    public Tag getLowestRelevantTag() {
        Tag lowestTag = Tag.values()[Tag.values().length-1];
        for (Tag tag : tags) {
            if (tag.ordinal() < lowestTag.ordinal() && (WorkbookExporter.getHoliday().contains(tag) || tag.equals(Tag.ALL))) lowestTag = tag;
        }
        return lowestTag;
    }

    public String getName() {
        StringBuilder formattedName = new StringBuilder();
        String[] parts = name().replace("____", " / ").replace("___", " - ").replace("__", "-").replace("_", " ").split(" ");
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (!p.isEmpty()) {
                String suffix;
                if (p.length() > 1) suffix = p.substring(1);
                else suffix = "!";

                if (p.charAt(p.length() - 1) == '0') { // If '0' always add CAPS
                    formattedName.append(p.replace("0", "").toUpperCase());
                } else if (i == 0) { // If first word add Caps
                    formattedName.append(p.toUpperCase().charAt(0)).append(suffix.toLowerCase());
                } else { // Otherwise add caps
                    formattedName.append(p.toLowerCase());
                }
                formattedName.append(" ");
            }
        }
        return formattedName.toString().replace("!", "").trim();
    }

    public String formatAmount() {
        int nights = isSmart() ? WorkbookExporter.getHoliday().smartNights() : WorkbookExporter.getHoliday().nights();

        float amount;
        float variance;
        float additionalVariance = getAdditionalVariance();
        if (constant) {
            amount = minAmount;
            variance = variation;
        } else {
            amount = (float) Math.ceil(minAmount * nights);
            variance = (float) Math.ceil((variation * nights) + (additionalVariance * variation * nights));
        }
        return (int) Math.ceil(amount) + ((int) variance != 0 ? "-" + Math.round(amount+variance) : ""); // ?
    }

    private float getAdditionalVariance() {
        Holiday holiday = WorkbookExporter.getHoliday();
        if (isEffectedByHeat()) {
            if (holiday.contains(Tag.HOT)) {
                return 0.75f;
            } else if (holiday.contains(Tag.WARM)) {
                return 0.25f;
            }
        }
        return 0;
    }

    private boolean isEffectedByHeat() {
        return this.equals(Item.T__SHIRTS) || this.equals(Item.SMART_SHIRTS) || this.equals(Item.PANTS) || this.equals(Item.SHORTS);
    }

    public boolean isSmart() {
        for (Tag tag : tags) if (tag.equals(Tag.SMART)) return true;
        for (Tag tag : requiredTags) if (tag.equals(Tag.SMART)) return true;
        return false;
    }

    public boolean contains(Tag givenTag) {
        for (Tag tag : tags) {
            if (tag.equals(givenTag)) return true;
        }
        return false;
    }

    public boolean containsRequired(Tag givenTag) {
        for (Tag tag : tags) {
            if (tag.equals(givenTag)) return true;
        }
        return false;
    }

    public float getMinAmount() {
        return minAmount;
    }

    public int getRequiredNights() {
        return requiredNights;
    }

    public int getRequiredParticipants() {
        return requiredParticipants;
    }

    public Tag[] getTags() {
        return tags;
    }

    public Tag[] getRequiredTags() {
        return requiredTags;
    }

    public boolean isRecommended() {
        Holiday holiday = WorkbookExporter.getHoliday();

        // Check required nights and participants.
        if (holiday.nights() >= requiredNights && holiday.participants().size() >= requiredParticipants) {
            // Check any required tags
            if (requiredTags.length > 0) {
                boolean aRequiredTag = false;
                for (Tag requiredTag : requiredTags) {
                    if (holiday.contains(requiredTag)) {
                        aRequiredTag = true;
                        break;
                    }
                }
                if (!aRequiredTag) return false;
            }
            // Now check at least one normal tag appears in holiday
            for (Tag tag : tags) {
                if (tag.equals(Tag.ALL)) return true;
                if (holiday.contains(tag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCommon() {
        return appliesTo == null;
    }

    public boolean appliesTo(ItemSet givenItemSet) {
        if (appliesTo == null) return false;
        for (ItemSet itemSet : appliesTo) {
            if (itemSet.equals(givenItemSet)) return true;
        }
        return false;
    }

    public ItemSet[] getAppliesTo() {
        return appliesTo;
    }
}

// GYM clothes (tag should add 1 to certain things (socks)) [OR add gym socks (etc.) as an item]
// AND hot multiplier (When hot, t-shirts could be multiplied by i.e. 1.5)

// Make it so smart casual counts as smart

// Dresses should be done daily.
// Review books