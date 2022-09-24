package com.assesment.addressbook.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Contact(
    var customerId: String?,
    var companyName: String?,
    var contactName: String?,
    var contactTitle: String?,
    var address: String?,
    var city: String?,
    var email: String?,
    var postalCode: String?,
    var country: String?,
    var phone: String?,
    var fax: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)