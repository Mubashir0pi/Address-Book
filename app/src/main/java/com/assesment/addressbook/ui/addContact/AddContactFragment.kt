package com.assesment.addressbook.ui.addContact


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.assesment.addressbook.MainActivity

import com.assesment.addressbook.R
import com.assesment.addressbook.data.Contact
import com.assesment.addressbook.databinding.FragmentAddContactBinding
import com.assesment.addressbook.di.ViewModelProviderFactory
import com.assesment.addressbook.util.OPTIONS
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class AddContactFragment : DaggerFragment() {


    private lateinit var  binding: FragmentAddContactBinding

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: AddContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_contact, container, false)

        binding.setLifecycleOwner(this.viewLifecycleOwner)

        viewModel = ViewModelProviders.of(this, factory).get(AddContactViewModel::class.java)

        val args = AddContactFragmentArgs.fromBundle(arguments!!)

        if (args.id != -1){
            val contact = viewModel.getContactById(args.id)
            contact.observe(this, Observer {
                binding.contact = it
            })
        }

        binding.customerId.doAfterTextChanged {
            viewModel.customerId = it.toString()
        }
        binding.companyName.doAfterTextChanged {
            viewModel.contactName = it.toString()
        }
        binding.companyName.doAfterTextChanged {
            viewModel.companyName = it.toString()
        }
        binding.titleEditText.doAfterTextChanged {
            viewModel.contactTitle = it.toString()
        }
        binding.address.doAfterTextChanged {
            viewModel.address = it.toString()
        }
        binding.city.doAfterTextChanged {
            viewModel.city = it.toString()
        }
        binding.postalCode.doAfterTextChanged {
            viewModel.postalCode = it.toString()
        }
        binding.country.doAfterTextChanged {
            viewModel.country = it.toString()
        }
        binding.fax.doAfterTextChanged {
            viewModel.fax = it.toString()
        }
        binding.phoneNumberEditText.doAfterTextChanged {
            viewModel.phone = it.toString()
        }

        binding.emailEditText.doAfterTextChanged {
            viewModel.email = it.toString()
        }



        binding.saveButton.setOnClickListener {
            if (args.id == -1) {
                Timber.d(" contact detail is ${viewModel.companyName} ${viewModel.contactName}")
                viewModel.saveContact(Contact(
                    viewModel.customerId,
                    viewModel.companyName,
                    viewModel.contactName,
                    viewModel.contactTitle,
                    viewModel.address,
                    viewModel.city,
                    viewModel.email,
                    viewModel.postalCode,
                    viewModel.country,
                    viewModel.phone,
                    viewModel.fax))
                it.findNavController().navigate(AddContactFragmentDirections
                    .actionAddContactFragmentToContactsFragment())
            } else {
                Timber.d(" contact name is ${viewModel.contactName}")
                viewModel.updateContact(Contact(
                    viewModel.customerId,
                    viewModel.companyName,
                    viewModel.contactName,
                    viewModel.contactTitle,
                    viewModel.address,
                    viewModel.city,
                    viewModel.email,
                    viewModel.postalCode,
                    viewModel.country,
                    viewModel.phone,
                    viewModel.fax,
                    args.id))

                it.findNavController().navigate(AddContactFragmentDirections
                    .actionAddContactFragmentToContactsFragment())
            }
        }

        binding.cancelButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this)
        {
            // handling back button
            findNavController().navigate(AddContactFragmentDirections.actionAddContactFragmentToContactsFragment(), OPTIONS)
        }

        return binding.root
    }



    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Add New Contact"
    }
}
