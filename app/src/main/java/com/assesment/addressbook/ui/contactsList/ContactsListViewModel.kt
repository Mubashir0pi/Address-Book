package com.assesment.addressbook.ui.contactsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.addressbook.data.Contact
import com.assesment.addressbook.data.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val getAllContacts = repository.getAllContacts()

    fun findContactByName(query: String): LiveData<List<Contact>> {
        return repository.findContactByName(query)
    }
    fun saveContact(contact: Contact){
        insertContact(contact)
    }

    private fun insertContact(contact: Contact){
        viewModelScope.launch {
            repository.insertContact(contact)
        }

    }

}