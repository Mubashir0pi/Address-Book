package com.assesment.addressbook.ui.addContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.addressbook.data.Contact
import com.assesment.addressbook.data.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddContactViewModel @Inject constructor(private val repository: Repository): ViewModel()  {
    var customerId= ""
    var companyName= ""
    var contactName= ""
    var contactTitle= ""
    var address= ""
    var city= ""
    var email= ""
    var postalCode= ""
    var country= ""
    var phone= ""
    var fax= ""
    var profilePictureUrl= ""
    private fun updateRepositoryContact(contact: Contact){
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }

    fun updateContact(contact: Contact){
        updateRepositoryContact(contact)
    }

    fun saveContact(contact: Contact){
        insertContact(contact)
    }

    private fun insertContact(contact: Contact){
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun getContactById(contactId: Int) = repository.getContactById(contactId)
}