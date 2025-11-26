package com.youapps.users_management.ui.registration
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youapps.designsystem.components.lists.CarouselState
import com.youapps.designsystem.components.menus.DropDownMenuData
import com.youapps.designsystem.components.menus.DropDownMenuItemData
import com.youapps.designsystem.components.menus.ImageMediaType
import com.youapps.onlybeans.R
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.onlybeans.domain.services.InputRuleType
import com.youapps.onlybeans.domain.services.OBFormValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class OBRegistrationViewModel(
    private val applicationContext : Context,
    private val savedStateHandle: SavedStateHandle,
    private val usersRepository : OBUsersRepositoryInterface
)  : ViewModel() {

    private val _profileState = MutableStateFlow<OBRegistrationScreenState>(OBRegistrationScreenState.Loading)


    private val _countriesList : MutableStateFlow<DropDownMenuData> = MutableStateFlow<DropDownMenuData>(DropDownMenuData(
        items = List(5){
            DropDownMenuItemData(
                label = "label$it"
            )
        }
    ))

    private val _citiesList : MutableStateFlow<DropDownMenuData> = MutableStateFlow<DropDownMenuData>(DropDownMenuData(
        items = List(5){
            DropDownMenuItemData(
                label = "label$it"
            )
        }
    ))

    val countryCodes: Map<String, String> =
        applicationContext.resources.getStringArray(R.array.country_codes_to_prefixes_and_names)
            .associate {
                it.split("|").let { (key, value) ->
                    key to value
                }
            }



    init {
        fetchMyProfile()
    }


    private fun fetchMyProfile() {
        viewModelScope.launch {
            runCatching {
                return@runCatching usersRepository.getCurrentUserData()!!
            }.onFailure {
                _profileState.update {
                    OBRegistrationScreenState.Error(errorType = DomainErrorType.Undefined)
                }
            }.onSuccess { data->
                updateProfilePicture(uri = data.profilePicture)
                updateCoverPicture(uri = data.coverPicture)
                updateProfileDescription(text = data.profileDescription)
                updateStatus(status = data.status)
                data.address?.let {
                    updateCountry(country = it.country)
                    it.city?.run {
                        updateCity(city = this)
                    }
                    it.location?.run {
                        updateLocation(location = this)
                    }
                }
                data.phone?.run {
                    updatePhoneNumber(phone = this)
                }
                data.myCoffeeSpace?.gallery?.run {
                    updateCoffeeSpaceCarouselImages(this)
                }
                _profileState.update {
                    OBRegistrationScreenState.Success(userProfile = data)
                }
            }
        }
    }

    fun updateProfilePicture(uri : String) {
       viewModelScope.launch {
           withContext(Dispatchers.IO){
               savedStateHandle[PROFILE_PICTURE_KEY] = uri
           }
       }
    }


    fun updateCoverPicture(uri : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[COVER_PICTURE_KEY] = uri
            }
        }
    }

    fun updateProfileDescription(text : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_DESC_KEY] = text
            }
        }
    }

    fun updateStatus(status : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_STATUS_KEY] = status
            }
        }
    }

    fun updateCountry(country : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_COUNTRY_KEY] = country
            }
        }
    }

    fun updateCity(city : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_CITY_KEY] = city
            }
        }
    }

    fun updateLocation(location : OBLocation) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_LOCATION_KEY] = location
            }
        }
    }

    fun updatePhoneNumber(phone : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_PHONE_KEY] = phone
            }
        }
    }

    fun setSelectedCountryPrefix(data : DropDownMenuItemData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_PHONE_COUNTRY_PREFIX_KEY] = "${data.label}|${data.icon.toString()}"
            }
        }
    }



    fun updateCoffeeSpaceCarouselImages(coffeeSpaceImages  : List<String>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[COFFEE_SPACE_CAROUSEL_KEY] = coffeeSpaceImages.joinToString("||")
            }
        }
    }

    fun deleteCoffeeSpaceCarouselImage(index: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
             val newUpdate = getCoffeeSpaceCarouselImages().firstOrNull()?.images?.filterIndexed { currentIndex, string ->
                 currentIndex != index
             }
             newUpdate?.run {
              savedStateHandle[COFFEE_SPACE_CAROUSEL_KEY] = newUpdate.joinToString("||")
             }
            }
        }
    }

     fun getProfilePicture() :  Flow<String?> = savedStateHandle.getStateFlow(key = PROFILE_PICTURE_KEY,null)

     fun getCoverPicture() : Flow<String?> =  savedStateHandle.getStateFlow(key = COVER_PICTURE_KEY,null)

    fun getProfileStatus() : Flow<InputRuleCheckState> = savedStateHandle.getStateFlow<String?>(key = PROFILE_STATUS_KEY,null).map { data->
        return@map  data?.takeIf {
            OBFormValidator.matchesRequiredRule(it)
        }?.run {
            if (OBFormValidator.matchesOnlyLettersRule(this)){
                InputRuleCheckState.Valid(
                    input = data
                )
            } else {
                InputRuleCheckState.Invalid(
                    input = data,
                    brokenRule = InputRuleType.LETTERS_ONLY
                )
            }

        } ?: InputRuleCheckState.Invalid(
            input = data,
            brokenRule = InputRuleType.REQUIRED
        )
    }

     fun getProfileDescription() : Flow<InputRuleCheckState> = savedStateHandle.getStateFlow<String?>(key = PROFILE_DESC_KEY,null).map { data->
         return@map  data?.takeIf {
             OBFormValidator.matchesRequiredRule(it)
         }?.run {
             if (OBFormValidator.matchesMinCharsRule(this)){
                 if (OBFormValidator.matchesMaxCharsRule(this)){
                     InputRuleCheckState.Valid(
                         input = data
                     )
                 } else {
                     InputRuleCheckState.Invalid(
                         input = data,
                         brokenRule = InputRuleType.MAX_LENGTH
                     )
                 }

             } else {
                 InputRuleCheckState.Invalid(
                     input = data,
                     brokenRule = InputRuleType.MIN_LENGTH
                 )
             }
         } ?: InputRuleCheckState.Invalid(
             input = data,
             brokenRule = InputRuleType.REQUIRED
         )
     }


    fun getPhone() : Flow<InputRuleCheckState> = savedStateHandle.getStateFlow<String?>(key = PROFILE_PHONE_KEY,null).map { data->
        return@map  data?.takeIf {
            OBFormValidator.matchesRequiredRule(it)
        }?.run {
            if (OBFormValidator.matchesOnlyDigitsRule(this)){
                InputRuleCheckState.Valid(
                    input = data
                )
            } else {
                InputRuleCheckState.Invalid(
                    input = data,
                    brokenRule = InputRuleType.PHONE_FORMAT
                )
            }

        } ?: InputRuleCheckState.Invalid(
            input = data,
            brokenRule = InputRuleType.REQUIRED
        )
    }

    fun getSelectedPhonePrefix() : Flow<DropDownMenuItemData?>
    = savedStateHandle.getStateFlow<String?>(key = PROFILE_PHONE_COUNTRY_PREFIX_KEY,Locale.getDefault().run {
        "${countryCodes[country]}|${applicationContext.getString(R.string.countries_api_url,country)}"
    }).map {
        it?.split("|").runCatching {
            DropDownMenuItemData(
                label = this!![0],
                icon = ImageMediaType.Url(url = this[1])
            )
        }.getOrNull()
    }


    fun getFistName() : Flow<String?> = _profileState.map {
        if (it is OBRegistrationScreenState.Success) {
            it.userProfile.firstName
        } else null
    }

    fun getLastName() : Flow<String?> = _profileState.map {
        if (it is OBRegistrationScreenState.Success) {
            it.userProfile.secondName
        } else null
    }

    fun getEmail() : Flow<String?> = _profileState.map {
        if (it is OBRegistrationScreenState.Success) {
            it.userProfile.email
        } else null
    }



    fun getCountriesList() : Flow<DropDownMenuData> = _countriesList

    fun getCitiesList() : Flow<DropDownMenuData> = _citiesList





    fun getCountry() : Flow<InputRuleCheckState> = savedStateHandle.getStateFlow<String?>(key = PROFILE_COUNTRY_KEY,null).map { data->
        return@map  data?.takeIf {
            OBFormValidator.matchesRequiredRule(it)
        }?.run {
            if (OBFormValidator.matchesOnlyLettersRule(this)){
                InputRuleCheckState.Valid(
                    input = data
                )
            } else {
                InputRuleCheckState.Invalid(
                    input = data,
                    brokenRule = InputRuleType.LETTERS_ONLY
                )
            }
        } ?: InputRuleCheckState.Invalid(
            input = data,
            brokenRule = InputRuleType.REQUIRED
        )
    }

    fun getCity() : Flow<InputRuleCheckState> = savedStateHandle.getStateFlow<String?>(key = PROFILE_CITY_KEY,null).map { data->
        return@map  data?.takeIf {
            OBFormValidator.matchesRequiredRule(it)
        }?.run {
            if (OBFormValidator.matchesOnlyLettersRule(this)){
                InputRuleCheckState.Valid(
                    input = data
                )
            } else {
                InputRuleCheckState.Invalid(
                    input = data,
                    brokenRule = InputRuleType.LETTERS_ONLY
                )
            }
        } ?: InputRuleCheckState.Invalid(
            input = data,
            brokenRule = InputRuleType.REQUIRED
        )
    }
    fun getLocation() : Flow<OBLocation?> = savedStateHandle.getStateFlow<String?>(key = PROFILE_LOCATION_KEY,null).map { encodedLocation->
        encodedLocation?.run {
            OBLocation.fromString(encodedLocation)
        }
      }

    fun getCoffeeSpaceCarouselImages() : Flow<CarouselState.Loaded> = savedStateHandle.getStateFlow<String?>(key = COFFEE_SPACE_CAROUSEL_KEY,null).map {
        CarouselState.Loaded(it?.split("||") ?: listOf())
    }

    fun getCountryCodesDropDownMenuData() : Flow<DropDownMenuData?> = flow {

        emit(DropDownMenuData(
            items = Locale.getAvailableLocales().filter {
                it.country.isNotBlank() && countryCodes[it.country] != null
            }.map {
                DropDownMenuItemData(
                    label = "${countryCodes[it.country]}",
                    icon = ImageMediaType.Url(
                        url = applicationContext.getString(R.string.countries_api_url,it.country)
                    )
                )
            }
        ))
    }










    companion object{
        private const val PROFILE_PICTURE_KEY = "profile_picture"
        private const val COVER_PICTURE_KEY = "cover_picture"
        private const val PROFILE_DESC_KEY = "profile_desc"
        private const val PROFILE_STATUS_KEY = "profile_status"

        private const val PROFILE_COUNTRY_KEY = "profile_country"

        private const val PROFILE_CITY_KEY = "profile_city_"

        private const val PROFILE_LOCATION_KEY = "profile_location"

        private const val PROFILE_PHONE_KEY = "profile_phone"

        private const val PROFILE_PHONE_COUNTRY_PREFIX_KEY = "profile_phone_country_prefix"

        private const val COFFEE_SPACE_CAROUSEL_KEY = "coffee_space_carousel"
    }


}