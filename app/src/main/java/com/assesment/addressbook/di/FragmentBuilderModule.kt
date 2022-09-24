package com.assesment.addressbook.di

import com.assesment.addressbook.di.addContact.AddContactViewModelModule
import com.assesment.addressbook.di.contactDetails.ContactDetailsViewModelModule
import com.assesment.addressbook.di.contactsList.ContactsListViewModelModule
import com.assesment.addressbook.ui.addContact.AddContactFragment
import com.assesment.addressbook.ui.contactDetails.ContactDetailsFragment
import com.assesment.addressbook.ui.contactsList.ContactsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [ContactsListViewModelModule::class])
    abstract fun contributeContactsFragment() : ContactsListFragment

    @ContributesAndroidInjector(modules = [AddContactViewModelModule::class])
    abstract fun contributeAddContactFragment() : AddContactFragment

    @ContributesAndroidInjector(modules = [ContactDetailsViewModelModule::class])
    abstract fun contributeContactDetailsFragment() : ContactDetailsFragment
}