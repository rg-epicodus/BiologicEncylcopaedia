

# Welcome to the GRAIL Biologic Encyclopaedia

GRAIL Biologic Encyclopaedia is a project for the Java Course Week 4 Code Review at [Epicodus](https://www.epicodus.com/) Code School.

GRAIL Biologic Encyclopaedia currently uses a simplified taxonomy nomenclature strating with Kingdom, with respect to the Life and Domain parts have been omitted for programming purposes, hopefully to be added on future releases.

GRAIL Biologic Encyclopaedia offers a few key features:
- Enter in information for an Item
- Enter in information for Places
- Retrieve and View information via Postman or JSON 
- Pull data from our API for your project


## Setup/Installation Requirements

* Clone the repo
* Open in your favorite editor
* Run app.java
* Download Postman to run the API through for the project:
* Set the HTTP request type to POST so we can create a record before we can read it
![ScreenShot](screenshot01.jpg)
* Point the URL to localhost:4567/restaurants/new
* Select the radio button marked "raw", and the type to " JSON application/json" in the dropdown menu.
* Select the Body radio button, and copy in JSON below.

<pre><code>
  }
    "commonName": "Chicken",
    "kingdom":"Animalia",
    "phylum":"Chordata",
    "class": "Aves",
    "order": "Galleformes",
    "family": "Phasianidae",
    "subfamily": "Phasianinae"
    "genus": "Gallus",
    "species": "G. gallus",
    "subspecies": "G. g. domesticus",
    "Trinomial Name":"Gallus gallus domesticus",
    "domesticated": "true",
    "yearIntroduced": "1758",
    "notes": "I saw this on a farm",
    "image": "chicken.jpg"
  }
</code></pre>

* Let's retrieve them. Open a new Postman tab, and:
![ScreenShot](screenshot02.jpg)
* Set the type to GET, Point the URL to localhost:4567/restaurants
* Hit Send!
* I don't know Perry, Magnets and Science, amazing stuff here....


## Specifications

| Behavior      | Example Input         | Example Output        |
| ------------- | ------------- | ------------- |
|   |  |  |
|   |  |  |
|   |  |  |
|   |  |  |

## Known Bugs
* "Rich Garrick" <richg341@gmail.com>

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D


## License
The MIT License (MIT)
Copyright 2017 Rich Garrick

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
