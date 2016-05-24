from __future__ import print_function

import json
import urllib
import boto3

print('Loading function')

s3 = boto3.client('s3')


def lambda_handler(event, context):
    #print("Received event: " + json.dumps(event, indent=2))

    # Get the object from the event and show its content type
    #bucket = event['Records'][0]['s3']['bucket']['name']
    #key = urllib.unquote_plus(event['Records'][0]['s3']['object']['key']).decode('utf8')
    try:
        #response = s3.get_object(Bucket=bucket, Key=key)
        response = s3.list_objects(Bucket='csula-jpl-dm',
                                   Delimiter=',',
                                   Encoding='url',
                                   MaxKeys=1000)
        print("CONTENT TYPE: " + response['ContentType'])
        return response['ContentType']
    except Exception as e:
        print(e)
        print('Error listing from bucket {}. Make sure they exist and your bucket is in the same region as this function.'.format(bucket))
        raise e
