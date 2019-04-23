
def add_tags(resources_fragment, tag_names, tag_values):
    new_dict={}
    for k,v in resources_fragment.items():
        new_dict[k]=v
        if('Properties' not in v):
            new_dict[k]['Properties']={}
        if('Tags' not in new_dict[k]['Properties']):
                new_dict[k]['Properties']['Tags']=[]
        new_dict[k]['Properties']['Tags'].extend( map( lambda t: {'Key':t[0],'Value':t[1]}, zip( tag_names, tag_values)))

    return new_dict

def tag_adder(event, context):
    return {
        "requestId": event['requestId'],
        "status": "success",
        "fragment": add_tags(event['fragment'], event['params']['TagNames'],event['params']['TagValues'])
    }

def handler(event, context):
print("event", event)
print("context",context)
try:
    ans = tag_adder(event,context)
    print("returning", ans)
    return ans
except BaseException as ex:
    print("Error - "+str(ex))
    return {
        "requestId": event['requestId'],
        "status": "ERROR - "+str(ex),
        "fragment": {}
    }

