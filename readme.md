

## TEST Adserver

>curl http://localhost:8081/adserver/info


>curl http://localhost:8081/ad


## GITHUB ASSESSMENT FOR SMART INTERNET PROJECT:

Git Assessment:

### Simple Ad Server

-------------------------------------

This doc contains requirements for a coding exercise that involves building a simple HTTP-based ad server.

 

#### Overview

--------

You will be building a simple web application that allows a user to create ad campaigns. You should demonstrate that your code meets the functional requirements described below via unit and integration tests. There should be instructions for deploying and running the application, ideally expressed via code/configuration and not prose.

  

  Use the language with which you feel most comfortable.

   

   Share your project via a Git repository in GitHub.

    

    Be prepared to walk through your code, discuss your thought process, and talk through how you might monitor and scale this application. You should also be able to demo a running instance of the host.


     Functional Requirements

     -----------------------


       == Create Ad Campaign via HTTP POST


         A user should be able to create an ad campaign by sending a POST request to the ad server at http://<host>/ad.  The body of the POST request must be a JSON object containing the following data:


          {

            "partner_id": "unique_string_representing_partner',

            "duration": "int_representing_campaign_duration_in_seconds_from_now"

            "ad_content": "string_of_content_to_display_as_ad"

          }
           

           The server should enforce the following invariant upon receiving a request to create a new campaign.
            

            * Only one active campaign can exist for a given partner.


             If an error is encountered, the ad server must return an appropriate response and indicate the problem to the user.  The response format is left up to the discretion of the implementer.
              

              Storing campaign data in memory or a cookie is totally fine.
                 

         == Fetch Ad Campaign for a Partner

          

           A partner should be able to get their ad data by sending a GET request to the ad server at http://<host>/ad/<partner_id>.  Response can be delivered as a JSON object representing the active ad
            

            If the current time is greater than a campaign's creation time + duration, then the server's response should be an error indicating that no active ad campaigns exist for the specified partner.
             

     Bonus

     -----

     The following are not required but might be nice additions to the exercise.

     * Describe a fault tolerant deployment topology for your application, and the types of failures it would and would not be resilient to.

     * Discuss the advantages and disadvantages of your persistence mechanism.

     * Add a URL to return a list of all campaigns as JSON.

     * Add support for multiple ad campaigns per partner.

## END OF GITHUB ASSESSMENT
