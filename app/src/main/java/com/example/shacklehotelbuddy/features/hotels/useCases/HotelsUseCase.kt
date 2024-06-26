package com.example.shacklehotelbuddy.features.hotels.useCases

import com.example.shacklehotelbuddy.base.api.models.RequestResult
import com.example.shacklehotelbuddy.features.hotels.api.IHotelsApiRepository
import com.example.shacklehotelbuddy.features.hotels.models.Hotel
import com.example.shacklehotelbuddy.features.search.db.ISearchParametersDbRepository
import com.example.shacklehotelbuddy.utils.IUseCase
import javax.inject.Inject

/**
 * Hotels use case.
 *
 * @property searchParametersDbRepository Search parameters database repository
 * @property hotelsApiRepository Hotels API repository
 * @constructor Create [HotelsUseCase]
 */
class HotelsUseCase @Inject constructor(
    private val searchParametersDbRepository: ISearchParametersDbRepository,
    private val hotelsApiRepository: IHotelsApiRepository
) : IUseCase {
    /**
     * Get hotels by last request.
     *
     * @return [RequestResult]
     */
    suspend fun getHotelsByLastRequest(): RequestResult<List<Hotel>> {
        searchParametersDbRepository.getLastSearchParameters(count = 1).firstOrNull()?.let { searchParameters ->
            return hotelsApiRepository.getHotels(searchParameters = searchParameters)
        } ?: return RequestResult.Failed(code = 0, errorMessage = "No search parameters found")
    }
}