from bs4 import BeautifulSoup
from urllib import request
from urllib import parse
import sys

url = "http://srh.bankofchina.com/search/whpj/search.jsp"
Form_Data = {}
Form_Data['erectDate'] = ''
Form_Data['nothing'] = ''
# Form_Data['pjname'] = '1316'
data=[1,2];

# i=sys.argv[1];
def func(i):
    Form_Data['pjname']=i;
    data = parse.urlencode(Form_Data).encode('utf-8')
    html = request.urlopen(url,data).read()
    soup = BeautifulSoup(html,'html.parser')

    div = soup.find('div', attrs = {'class':'BOC_main publish'})
    table = div.find('table')
    tr = table.find_all('tr')
    td = tr[1].find_all('td')
    print (td)
    # return td

func(1314);
