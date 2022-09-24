package com.assesment.addressbook.di.contactsList

import androidx.lifecycle.ViewModel
import com.assesment.addressbook.di.ViewModelKey
import com.assesment.addressbook.ui.contactsList.ContactsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactsListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactsListViewModel::class)
    abstract fun bindContactsListViewModel(contactsListViewModel: ContactsListViewModel): ViewModel

}