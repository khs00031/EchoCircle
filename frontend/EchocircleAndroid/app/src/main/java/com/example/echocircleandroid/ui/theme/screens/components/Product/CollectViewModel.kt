package com.example.echocircleandroid.ui.theme.screens.components.Product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.data.CollectRequest
import com.example.echocircleandroid.ui.theme.screens.data.CollectResponse
import com.example.echocircleandroid.ui.theme.screens.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _visitCollect = MutableStateFlow(false)
    val visitCollect: StateFlow<Boolean> = _visitCollect

    private val _companyCollect = MutableStateFlow(false)
    val companyCollect: StateFlow<Boolean> = _companyCollect

    private val _collectResponse = MutableStateFlow<CollectResponse?>(null)
    val collectResponse: StateFlow<CollectResponse?> = _collectResponse


    fun fetchProduct(product: Product) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCollectable(product.id)
                _collectResponse.value = response

                if (response.httpStatus == "ACCEPTED") {
                    _isLoading.value = true
                    _visitCollect.value = response.visitCollect
                    _companyCollect.value = response.companyCollect
                }

            } catch (e: Exception) {
                _collectResponse.value = CollectResponse(
                    httpStatus = "SERVER ERROR",
                    visitCollect = false,
                    companyCollect = false
                )
            }
        }
    }

    fun fetchProductCollectableSub(product: Product) {
        viewModelScope.launch {
            try {
                val request = CollectRequest(
                    product.category,
                    product.company,
                    product.size,
                    product.year.toString()
                )

                val response = RetrofitInstance.api.getCollectableSub(request)
                _collectResponse.value = response

                if (response.httpStatus == "ACCEPTED") {
                    _isLoading.value = true
                    _visitCollect.value = response.visitCollect
                    _companyCollect.value = response.companyCollect
                }

            } catch (e: Exception) {
                _collectResponse.value = CollectResponse(
                    httpStatus = "SERVER ERROR",
                    visitCollect = false,
                    companyCollect = false
                )
            }
        }
    }
}