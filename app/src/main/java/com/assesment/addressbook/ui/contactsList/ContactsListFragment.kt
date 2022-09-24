package com.assesment.addressbook.ui.contactsList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.assesment.addressbook.MainActivity
import com.assesment.addressbook.R
import com.assesment.addressbook.data.Contact
import com.assesment.addressbook.databinding.FragmentContactsListBinding
import com.assesment.addressbook.di.ViewModelProviderFactory
import com.assesment.addressbook.util.OPTIONS
import dagger.android.support.DaggerFragment
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.IOException
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class ContactsListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: ContactsListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentContactsListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(ContactsListViewModel::class.java)


        val adapter = ContactsAdapter(context!!, ContactsClickListener {
            val id = it.id
            this.findNavController().navigate(
                ContactsListFragmentDirections
                    .actionContactsListFragmentToContactDetailsFragment(id)
            )
        })

        binding.contactList.adapter = adapter

        readAndInsertData()
        subscribeUI(adapter)


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                searchRecord(adapter,s.toString())
                }else{
                    subscribeUI(adapter)

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_contactsListFragment_to_addContactFragment, null, OPTIONS)
        }

        return binding.root

    }

    private fun subscribeUI(adapter: ContactsAdapter) {
        viewModel.getAllContacts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.distinctBy { it.customerId })
            }
        })


    }
    private fun searchRecord(adapter: ContactsAdapter,query: String) {

        viewModel.findContactByName(query).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.distinctBy { it.customerId })
            }
        })

    }

    private fun readAndInsertData() {
        try {
            val istream = this.context!!.assets.open("ab.xml")
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(istream)
            val nList = doc.getElementsByTagName("Contact")
            for (i in 0 until nList.getLength()) {
                if (nList.item(0).getNodeType().equals(Node.ELEMENT_NODE)) {

                    val element = nList.item(i) as Element
                    var contact = Contact(
                        getNodeValue("CustomerID", element),
                        getNodeValue("CompanyName", element),
                        getNodeValue("ContactName", element),
                        getNodeValue("ContactTitle", element),
                        getNodeValue("Address", element),
                        getNodeValue("City", element),
                        getNodeValue("Email", element),
                        getNodeValue("PostalCode", element),
                        getNodeValue("Country", element),
                        getNodeValue("Phone", element),
                        getNodeValue("Fax", element)
                    )
                    viewModel.saveContact(
                        contact
                    )

                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }

    }


    protected fun getNodeValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.getFirstChild()
                while (child != null) {
                    if (child.nodeType === Node.TEXT_NODE) {
                        return child.getNodeValue()
                    }
                }
            }
        }
        return ""
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Contact List"
    }
}
