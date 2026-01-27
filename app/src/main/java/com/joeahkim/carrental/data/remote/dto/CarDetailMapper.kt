package com.joeahkim.carrental.data.remote.dto

import com.joeahkim.carrental.domain.model.CarDetail

fun CarDetailDto.toCarDetail(): CarDetail {
    return CarDetail(
        id = id,
        make = make,
        model = model,
        pricePerDay = pricePerDay,
        numberPlate = numberPlate,
        imageUrl = imageUrl
    )
}