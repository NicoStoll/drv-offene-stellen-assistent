Voraussetzungen:
    docker
    git
    python 3
    python install manager "winget install 9NQ7512CXL7T"
    pip "py -m pip install" und "py -m pip install --upgrade pip"


docker:
    docker pull python
    docker run -i -d python
    docker container ls (-a)
    docker exec -it <name> bash

    docker commit <name> chromadb
    docker tag chromadb:latest chromadb:initial
    docker tag chromadb:initial nummerxy/hackathon:initial

    #docker run --mount "C:\Users\DRV-BUND\drv-offene-stellen-assistent\drv-offene-stellen-assistent":output -i -d chromadb
    #docker run --mount type=bind,source="C:\path\to\directory",target=/path/in/container <image-name>
    docker run --mount type=bind,\
    source="C:\Users\DRV-BUND\drv-offene-stellen-assistent\drv-offene-stellen-assistent",\
    target=/output -i -d chromadb bash
    docker exec -it chromadb bash


Chroma:
    py -m pip install chromadb
    mkdir output

    pip install --upgrade pip
    pip install -U pydantic_settings
    pip install -U pydantic
    #pip install "pydantic==1.*"
    python
    >>> import sys, chromadb
    >>> 
