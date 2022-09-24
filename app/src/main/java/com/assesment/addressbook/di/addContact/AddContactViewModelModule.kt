package com.assesment.addressbook.di.addContact

import androidx.lifecycle.ViewModel
import com.assesment.addressbook.di.ViewModelKey
import com.assesment.addressbook.ui.addContact.AddContactViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddContactViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddContactViewModel::class)
    abstract fun bindAddContactViewModel(addContactViewModel: AddContactViewModel): ViewModel

}