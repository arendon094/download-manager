import xml.etree.ElementTree as etree
import certifi
import requests

from lxml import html
from elasticsearch import Elasticsearch
from elasticsearch import helpers

class es_upload():
    def __init__(self,):
        f = open('es_link.txt','r')
        url_file = f.readline().strip()
        self.es = Elasticsearch([url_file],
                                port=443,
                                use_ssl=True,
                                verify_certs=True,
                                ca_certs=certifi.where())
        self.meta_id = 1
        self.meta_insert_dict = {}

        def bulk_builder_layer(self,array,layer_insert_dict):
            builder = {
                "_index":"jpldownload",
                "_type":"layers",
                "_id":layer_insert_dict["id"],
                "_source":{
                    'uuid':layer_insert_dict["uuid"],
                    'productlabel':layer_insert_dict["productlabel"],
                    'producttype':layer_insert_dict["producttype"],
                    'serviceprotocol':layer_insert_dict["serviceprotocol"],
                    'endpoint':layer_insert_dict["endpoint"],
                    'layertitle':layer_insert_dict["layertitle"],
                    'layerservice':layer_insert_dict["layerservice"],
                    'layerprojection':layer_insert_dict["layerprojection"],
                    'tilesetname':layer_insert_dict["tilesetname"],
                    'abstract':layer_insert_dict["abstract"],
                    'title':layer_insert_dict["title"],
                    'metadata':layer_insert_dict["title"],
                    'legend':layer_insert_dict["legend"],
                    'bounding':{
                        'westbc':layer_insert_dict["westbc"],
                        'eastbc':layer_insert_dict["eastbc"],
                        'northbc':layer_insert_dict["northbc"],
                        'southbc':layer_insert_dict["southbc"]
                    },
                    'bounding18':{
                        'proj':layer_insert_dict["proja"],
                        'westbc':layer_insert_dict["westbca"],
                        'eastbc':layer_insert_dict["eastbca"],
                        'northbc':layer_insert_dict["northbca"],
                        'southbc':layer_insert_dict["southbca"]
                    },
                    'bounding20':{
                        'proj':layer_insert_dict["projb"],
                        'westbc':layer_insert_dict["westbcb"],
                        'eastbc':layer_insert_dict["eastbcb"],
                        'northbc':layer_insert_dict["northbcb"],
                        'southbc':layer_insert_dict["southbcb"]
                    },
                    'supersedes':layer_insert_dict["super"],
                    'replacedby':layer_insert_dict["replace"],
                    'derivedfrom':layer_insert_dict["derived"],
                    'sort':{
                        'producttype':layer_insert_dict["productkey"],
                        'layerprojection':layer_insert_dict["layerkey"]
                        }
                }
            }

            array.append(builder)

    def bulk_builder_meta(self,array,meta_insert_dict):
        builder = {
            "_index":"jpldownload",
            "_type":"meta",
            "_id":meta_insert_dict["id"],
            "_source":{
                'title':meta_insert_dict["title"],
                'creator':meta_insert_dict["creator"],
                'subject':meta_insert_dict["subject"],
                'description':meta_insert_dict["description"],
                'publisher':meta_insert_dict["publisher"],
                'contributor':meta_insert_dict["contributor"],
                'pubdate':meta_insert_dict["date"],
                'type':meta_insert_dict["type"],
                'identifier':meta_insert_dict["identifier"],
                'lang':meta_insert_dict["lang"],
                'relation':meta_insert_dict["relation"],
                'westbd':meta_insert_dict["xmin"],
                'eastbd':meta_insert_dict["xmax"],
                'northbd':meta_insert_dict["ymax"],
                'southbd':meta_insert_dict["ymin"],
                'placeName':meta_insert_dict["placeName"],
                'startdate':meta_insert_dict["tmin"],
                'stopdate':meta_insert_dict["tmax"],
                'rights':meta_insert_dict["rights"],
                'genfrom':meta_insert_dict["generated-from"]
            }
        }

        array.append(builder)
            
    def parse_xml(self,xml_file):
        tree = etree.parse(xml_file)
        root = tree.getroot()         
        layer_insert_dict = {}
        bulk_upload = []
        
        for layers in root:
            for layer in layers:
                if layer.tag == "Metadata":
                    self.scrape_html(layer.text,bulk_upload)
                #print(layer.tag,layer.attrib)
                if layer.tag == "bounding":
                    if 'proj' in layer.attrib:
                        pass            
                        #print(layer.attrib['proj'])            
                    for bounding in layer:
                        pass
                        #print(bounding.tag,bounding.attrib)
                else:
                    #print(layer.text)
                    if layer == "Sort":
                        pass
                        #print(layer.attrib["key"])
        #print(bulk_upload)
        helpers.bulk(self.es,bulk_upload)

    def scrape_html(self,html_link,array):
        f = open('lmmp_api.txt','r')
        lmmp_api = f.readline().strip()
        page = requests.get(lmmp_api+html_link).text
        doc = html.fromstring(page)
        meta_list = doc.cssselect('meta')
        placeHolder = []
        for num in range(1,len(meta_list)):
            names = meta_list[num].attrib["name"].split('.')
            content = meta_list[num].attrib["content"]
            if len(names) == 1:
                key = names[0]
                self.meta_insert_dict[key] = content
            elif names[1] == "coverage":
                if len(names) > 3:
                    key = names[2] + names[3]
                    self.meta_insert_dict[key] = content
                else:
                    key = names[2]
                    placeHolder.append(content)
                    self.meta_insert_dict[key] = placeHolder
            else:
                key = names[1]
                self.meta_insert_dict[key] = content
        self.meta_insert_dict["id"] = self.meta_id
        self.bulk_builder_meta(array,self.meta_insert_dict)
        self.meta_id += 1

if __name__ == '__main__':
    es_up = es_upload()
    es_up.parse_xml('layers.xml')
