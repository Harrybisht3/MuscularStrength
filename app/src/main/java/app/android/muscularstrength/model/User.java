package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bisht Bhawna on 8/14/2015.
 */
public class User  {

    @SerializedName("User Id")
    @Expose
    private String UserId;
    @SerializedName("First Name")
    @Expose
    private String FirstName;
    @SerializedName("Last Name")
    @Expose
    private String LastName;
    @Expose
    private String Email;
    @Expose
    private String Address;
    @Expose
    private String City;
    @Expose
    private String State;
    @Expose
    private String Zip;
    @Expose
    private String Country;
    @SerializedName("Full Image")
    @Expose
    private String FullImage;
    @SerializedName("Icon Image")
    @Expose
    private String IconImage;
    @SerializedName("Member Type")
    @Expose
    private String MemberType;
    @SerializedName("Payment Method")
    @Expose
    private String PaymentMethod;
    @SerializedName("Payment Interval")
    @Expose
    private String PaymentInterval;
    @SerializedName("body Type")
    @Expose
    private String bodyType;
    @SerializedName("skin Color")
    @Expose
    private String skinColor;
    @Expose
    private String Gender;
    @Expose
    private String hair;
    @Expose
    private String eyes;
    @Expose
    private String mouth;
    @SerializedName("top Upper")
    @Expose
    private String topUpper;
    @SerializedName("top Lower")
    @Expose
    private String topLower;
    @Expose
    private String bottom;
    @Expose
    private String bottom2;
    @SerializedName("tat toos")
    @Expose
    private String tatToos;
    @Expose
    private String Eyes;
    @Expose
    private String Head;
    @Expose
    private String Neck;
    @Expose
    private String Hands;
    @Expose
    private String LeftArm;
    @Expose
    private String RightArm;
    @Expose
    private String Chest;
    @Expose
    private String Waist;
    @Expose
    private String LeftLeg;
    @Expose
    private String RightLeg;
    @Expose
    private String BottomUpperLayer;
    @Expose
    private String BottomLowerLayer;
    @Expose
    private String accessoriesShoes;
    @Expose
    private String shoes;
    @Expose
    private String rightLeg;
    @Expose
    private String leftLeg;
    @Expose
    private String exclusiveNeck;
    @Expose
    private String exclusiveTopUpper;
    @Expose
    private String exclusiveTopLower;
    @Expose
    private String exclusiveLeftArm;
    @Expose
    private String exclusiveRightArm;
    @Expose
    private String exclusiveWaist;
    @Expose
    private String exclusiveLeftLeg;
    @Expose
    private String exclusiveRightLeg;
    @Expose
    private String exclusiveLeftFoot;
    @Expose
    private String exclusiveRightFoot;
    @Expose
    private String exclusiveLeftProp;
    @Expose
    private String exclusiveRightProp;
    @Expose
    private String exclusiveBottomUpperLayer;
    @Expose
    private String exclusiveBottomLowerLayer;
    @Expose
    private String exclusiveChestProp;
    @Expose
    private String avatarHolidayCostumes;
    @Expose
    private String avatarbackground;
    @SerializedName("account type")
    @Expose
    private String accountType;
    @SerializedName("User level")
    @Expose
    private String UserLevel;
    @Expose
    private String Status;

    /**
     *
     * @return
     * The UserId
     */
    public String getUserId() {
        return UserId;
    }

    /**
     *
     * @param UserId
     * The User Id
     */
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    /**
     *
     * @return
     * The FirstName
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     *
     * @param FirstName
     * The First Name
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     *
     * @return
     * The LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     *
     * @param LastName
     * The Last Name
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     *
     * @return
     * The Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     *
     * @param Email
     * The Email
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     *
     * @return
     * The Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     *
     * @param Address
     * The Address
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     *
     * @return
     * The City
     */
    public String getCity() {
        return City;
    }

    /**
     *
     * @param City
     * The City
     */
    public void setCity(String City) {
        this.City = City;
    }

    /**
     *
     * @return
     * The State
     */
    public String getState() {
        return State;
    }

    /**
     *
     * @param State
     * The State
     */
    public void setState(String State) {
        this.State = State;
    }

    /**
     *
     * @return
     * The Zip
     */
    public String getZip() {
        return Zip;
    }

    /**
     *
     * @param Zip
     * The Zip
     */
    public void setZip(String Zip) {
        this.Zip = Zip;
    }

    /**
     *
     * @return
     * The Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     *
     * @param Country
     * The Country
     */
    public void setCountry(String Country) {
        this.Country = Country;
    }

    /**
     *
     * @return
     * The FullImage
     */
    public String getFullImage() {
        return FullImage;
    }

    /**
     *
     * @param FullImage
     * The Full Image
     */
    public void setFullImage(String FullImage) {
        this.FullImage = FullImage;
    }

    /**
     *
     * @return
     * The IconImage
     */
    public String getIconImage() {
        return IconImage;
    }

    /**
     *
     * @param IconImage
     * The Icon Image
     */
    public void setIconImage(String IconImage) {
        this.IconImage = IconImage;
    }

    /**
     *
     * @return
     * The MemberType
     */
    public String getMemberType() {
        return MemberType;
    }

    /**
     *
     * @param MemberType
     * The Member Type
     */
    public void setMemberType(String MemberType) {
        this.MemberType = MemberType;
    }

    /**
     *
     * @return
     * The PaymentMethod
     */
    public String getPaymentMethod() {
        return PaymentMethod;
    }

    /**
     *
     * @param PaymentMethod
     * The Payment Method
     */
    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    /**
     *
     * @return
     * The PaymentInterval
     */
    public String getPaymentInterval() {
        return PaymentInterval;
    }

    /**
     *
     * @param PaymentInterval
     * The Payment Interval
     */
    public void setPaymentInterval(String PaymentInterval) {
        this.PaymentInterval = PaymentInterval;
    }

    /**
     *
     * @return
     * The bodyType
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     *
     * @param bodyType
     * The body Type
     */
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     *
     * @return
     * The skinColor
     */
    public String getSkinColor() {
        return skinColor;
    }

    /**
     *
     * @param skinColor
     * The skin Color
     */
    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    /**
     *
     * @return
     * The Gender
     */
    public String getGender() {
        return Gender;
    }

    /**
     *
     * @param Gender
     * The Gender
     */
    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    /**
     *
     * @return
     * The hair
     */
    public String getHair() {
        return hair;
    }

    /**
     *
     * @param hair
     * The hair
     */
    public void setHair(String hair) {
        this.hair = hair;
    }

    /**
     *
     * @return
     * The eyes
     */
    public String getEyes() {
        return eyes;
    }

    /**
     *
     * @param eyes
     * The eyes
     */
    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    /**
     *
     * @return
     * The mouth
     */
    public String getMouth() {
        return mouth;
    }

    /**
     *
     * @param mouth
     * The mouth
     */
    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    /**
     *
     * @return
     * The topUpper
     */
    public String getTopUpper() {
        return topUpper;
    }

    /**
     *
     * @param topUpper
     * The top Upper
     */
    public void setTopUpper(String topUpper) {
        this.topUpper = topUpper;
    }

    /**
     *
     * @return
     * The topLower
     */
    public String getTopLower() {
        return topLower;
    }

    /**
     *
     * @param topLower
     * The top Lower
     */
    public void setTopLower(String topLower) {
        this.topLower = topLower;
    }

    /**
     *
     * @return
     * The bottom
     */
    public String getBottom() {
        return bottom;
    }

    /**
     *
     * @param bottom
     * The bottom
     */
    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    /**
     *
     * @return
     * The bottom2
     */
    public String getBottom2() {
        return bottom2;
    }

    /**
     *
     * @param bottom2
     * The bottom2
     */
    public void setBottom2(String bottom2) {
        this.bottom2 = bottom2;
    }

    /**
     *
     * @return
     * The tatToos
     */
    public String getTatToos() {
        return tatToos;
    }

    /**
     *
     * @param tatToos
     * The tat toos
     */
    public void setTatToos(String tatToos) {
        this.tatToos = tatToos;
    }

    /**
     *
     * @return
     * The Eyes
     */
    public String getEyes1() {
        return Eyes;
    }

    /**
     *
     * @param Eyes
     * The Eyes
     */
    public void setEyes1(String Eyes) {
        this.Eyes = Eyes;
    }

    /**
     *
     * @return
     * The Head
     */
    public String getHead() {
        return Head;
    }

    /**
     *
     * @param Head
     * The Head
     */
    public void setHead(String Head) {
        this.Head = Head;
    }

    /**
     *
     * @return
     * The Neck
     */
    public String getNeck() {
        return Neck;
    }

    /**
     *
     * @param Neck
     * The Neck
     */
    public void setNeck(String Neck) {
        this.Neck = Neck;
    }

    /**
     *
     * @return
     * The Hands
     */
    public String getHands() {
        return Hands;
    }

    /**
     *
     * @param Hands
     * The Hands
     */
    public void setHands(String Hands) {
        this.Hands = Hands;
    }

    /**
     *
     * @return
     * The LeftArm
     */
    public String getLeftArm() {
        return LeftArm;
    }

    /**
     *
     * @param LeftArm
     * The LeftArm
     */
    public void setLeftArm(String LeftArm) {
        this.LeftArm = LeftArm;
    }

    /**
     *
     * @return
     * The RightArm
     */
    public String getRightArm() {
        return RightArm;
    }

    /**
     *
     * @param RightArm
     * The RightArm
     */
    public void setRightArm(String RightArm) {
        this.RightArm = RightArm;
    }

    /**
     *
     * @return
     * The Chest
     */
    public String getChest() {
        return Chest;
    }

    /**
     *
     * @param Chest
     * The Chest
     */
    public void setChest(String Chest) {
        this.Chest = Chest;
    }

    /**
     *
     * @return
     * The Waist
     */
    public String getWaist() {
        return Waist;
    }

    /**
     *
     * @param Waist
     * The Waist
     */
    public void setWaist(String Waist) {
        this.Waist = Waist;
    }

    /**
     *
     * @return
     * The LeftLeg
     */
    public String getLeftLeg() {
        return LeftLeg;
    }

    /**
     *
     * @param LeftLeg
     * The LeftLeg
     */
    public void setLeftLeg(String LeftLeg) {
        this.LeftLeg = LeftLeg;
    }

    /**
     *
     * @return
     * The RightLeg
     */
    public String getRightLeg() {
        return RightLeg;
    }

    /**
     *
     * @param RightLeg
     * The RightLeg
     */
    public void setRightLeg(String RightLeg) {
        this.RightLeg = RightLeg;
    }

    /**
     *
     * @return
     * The BottomUpperLayer
     */
    public String getBottomUpperLayer() {
        return BottomUpperLayer;
    }

    /**
     *
     * @param BottomUpperLayer
     * The BottomUpperLayer
     */
    public void setBottomUpperLayer(String BottomUpperLayer) {
        this.BottomUpperLayer = BottomUpperLayer;
    }

    /**
     *
     * @return
     * The BottomLowerLayer
     */
    public String getBottomLowerLayer() {
        return BottomLowerLayer;
    }

    /**
     *
     * @param BottomLowerLayer
     * The BottomLowerLayer
     */
    public void setBottomLowerLayer(String BottomLowerLayer) {
        this.BottomLowerLayer = BottomLowerLayer;
    }

    /**
     *
     * @return
     * The accessoriesShoes
     */
    public String getAccessoriesShoes() {
        return accessoriesShoes;
    }

    /**
     *
     * @param accessoriesShoes
     * The accessoriesShoes
     */
    public void setAccessoriesShoes(String accessoriesShoes) {
        this.accessoriesShoes = accessoriesShoes;
    }

    /**
     *
     * @return
     * The shoes
     */
    public String getShoes() {
        return shoes;
    }

    /**
     *
     * @param shoes
     * The shoes
     */
    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    /**
     *
     * @return
     * The rightLeg
     */
    public String getRightLeg1() {
        return rightLeg;
    }

    /**
     *
     * @param rightLeg
     * The rightLeg
     */
    public void setRightLeg1(String rightLeg) {
        this.rightLeg = rightLeg;
    }

    /**
     *
     * @return
     * The leftLeg
     */
    public String getLeftLeg1() {
        return leftLeg;
    }

    /**
     *
     * @param leftLeg
     * The leftLeg
     */
    public void setLeftLeg1(String leftLeg) {
        this.leftLeg = leftLeg;
    }

    /**
     *
     * @return
     * The exclusiveNeck
     */
    public String getExclusiveNeck() {
        return exclusiveNeck;
    }

    /**
     *
     * @param exclusiveNeck
     * The exclusiveNeck
     */
    public void setExclusiveNeck(String exclusiveNeck) {
        this.exclusiveNeck = exclusiveNeck;
    }

    /**
     *
     * @return
     * The exclusiveTopUpper
     */
    public String getExclusiveTopUpper() {
        return exclusiveTopUpper;
    }

    /**
     *
     * @param exclusiveTopUpper
     * The exclusiveTopUpper
     */
    public void setExclusiveTopUpper(String exclusiveTopUpper) {
        this.exclusiveTopUpper = exclusiveTopUpper;
    }

    /**
     *
     * @return
     * The exclusiveTopLower
     */
    public String getExclusiveTopLower() {
        return exclusiveTopLower;
    }

    /**
     *
     * @param exclusiveTopLower
     * The exclusiveTopLower
     */
    public void setExclusiveTopLower(String exclusiveTopLower) {
        this.exclusiveTopLower = exclusiveTopLower;
    }

    /**
     *
     * @return
     * The exclusiveLeftArm
     */
    public String getExclusiveLeftArm() {
        return exclusiveLeftArm;
    }

    /**
     *
     * @param exclusiveLeftArm
     * The exclusiveLeftArm
     */
    public void setExclusiveLeftArm(String exclusiveLeftArm) {
        this.exclusiveLeftArm = exclusiveLeftArm;
    }

    /**
     *
     * @return
     * The exclusiveRightArm
     */
    public String getExclusiveRightArm() {
        return exclusiveRightArm;
    }

    /**
     *
     * @param exclusiveRightArm
     * The exclusiveRightArm
     */
    public void setExclusiveRightArm(String exclusiveRightArm) {
        this.exclusiveRightArm = exclusiveRightArm;
    }

    /**
     *
     * @return
     * The exclusiveWaist
     */
    public String getExclusiveWaist() {
        return exclusiveWaist;
    }

    /**
     *
     * @param exclusiveWaist
     * The exclusiveWaist
     */
    public void setExclusiveWaist(String exclusiveWaist) {
        this.exclusiveWaist = exclusiveWaist;
    }

    /**
     *
     * @return
     * The exclusiveLeftLeg
     */
    public String getExclusiveLeftLeg() {
        return exclusiveLeftLeg;
    }

    /**
     *
     * @param exclusiveLeftLeg
     * The exclusiveLeftLeg
     */
    public void setExclusiveLeftLeg(String exclusiveLeftLeg) {
        this.exclusiveLeftLeg = exclusiveLeftLeg;
    }

    /**
     *
     * @return
     * The exclusiveRightLeg
     */
    public String getExclusiveRightLeg() {
        return exclusiveRightLeg;
    }

    /**
     *
     * @param exclusiveRightLeg
     * The exclusiveRightLeg
     */
    public void setExclusiveRightLeg(String exclusiveRightLeg) {
        this.exclusiveRightLeg = exclusiveRightLeg;
    }

    /**
     *
     * @return
     * The exclusiveLeftFoot
     */
    public String getExclusiveLeftFoot() {
        return exclusiveLeftFoot;
    }

    /**
     *
     * @param exclusiveLeftFoot
     * The exclusiveLeftFoot
     */
    public void setExclusiveLeftFoot(String exclusiveLeftFoot) {
        this.exclusiveLeftFoot = exclusiveLeftFoot;
    }

    /**
     *
     * @return
     * The exclusiveRightFoot
     */
    public String getExclusiveRightFoot() {
        return exclusiveRightFoot;
    }

    /**
     *
     * @param exclusiveRightFoot
     * The exclusiveRightFoot
     */
    public void setExclusiveRightFoot(String exclusiveRightFoot) {
        this.exclusiveRightFoot = exclusiveRightFoot;
    }

    /**
     *
     * @return
     * The exclusiveLeftProp
     */
    public String getExclusiveLeftProp() {
        return exclusiveLeftProp;
    }

    /**
     *
     * @param exclusiveLeftProp
     * The exclusiveLeftProp
     */
    public void setExclusiveLeftProp(String exclusiveLeftProp) {
        this.exclusiveLeftProp = exclusiveLeftProp;
    }

    /**
     *
     * @return
     * The exclusiveRightProp
     */
    public String getExclusiveRightProp() {
        return exclusiveRightProp;
    }

    /**
     *
     * @param exclusiveRightProp
     * The exclusiveRightProp
     */
    public void setExclusiveRightProp(String exclusiveRightProp) {
        this.exclusiveRightProp = exclusiveRightProp;
    }

    /**
     *
     * @return
     * The exclusiveBottomUpperLayer
     */
    public String getExclusiveBottomUpperLayer() {
        return exclusiveBottomUpperLayer;
    }

    /**
     *
     * @param exclusiveBottomUpperLayer
     * The exclusiveBottomUpperLayer
     */
    public void setExclusiveBottomUpperLayer(String exclusiveBottomUpperLayer) {
        this.exclusiveBottomUpperLayer = exclusiveBottomUpperLayer;
    }

    /**
     *
     * @return
     * The exclusiveBottomLowerLayer
     */
    public String getExclusiveBottomLowerLayer() {
        return exclusiveBottomLowerLayer;
    }

    /**
     *
     * @param exclusiveBottomLowerLayer
     * The exclusiveBottomLowerLayer
     */
    public void setExclusiveBottomLowerLayer(String exclusiveBottomLowerLayer) {
        this.exclusiveBottomLowerLayer = exclusiveBottomLowerLayer;
    }

    /**
     *
     * @return
     * The exclusiveChestProp
     */
    public String getExclusiveChestProp() {
        return exclusiveChestProp;
    }

    /**
     *
     * @param exclusiveChestProp
     * The exclusiveChestProp
     */
    public void setExclusiveChestProp(String exclusiveChestProp) {
        this.exclusiveChestProp = exclusiveChestProp;
    }

    /**
     *
     * @return
     * The avatarHolidayCostumes
     */
    public String getAvatarHolidayCostumes() {
        return avatarHolidayCostumes;
    }

    /**
     *
     * @param avatarHolidayCostumes
     * The avatarHolidayCostumes
     */
    public void setAvatarHolidayCostumes(String avatarHolidayCostumes) {
        this.avatarHolidayCostumes = avatarHolidayCostumes;
    }

    /**
     *
     * @return
     * The avatarbackground
     */
    public String getAvatarbackground() {
        return avatarbackground;
    }

    /**
     *
     * @param avatarbackground
     * The avatarbackground
     */
    public void setAvatarbackground(String avatarbackground) {
        this.avatarbackground = avatarbackground;
    }

    /**
     *
     * @return
     * The accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     *
     * @param accountType
     * The account type
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     *
     * @return
     * The UserLevel
     */
    public String getUserLevel() {
        return UserLevel;
    }

    /**
     *
     * @param UserLevel
     * The User level
     */
    public void setUserLevel(String UserLevel) {
        this.UserLevel = UserLevel;
    }

    /**
     *
     * @return
     * The Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     *
     * @param Status
     * The Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

}

