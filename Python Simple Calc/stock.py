import matplotlib.pyplot as plt

import ystockquote

# Scratchwork framework for each of the procedures and stuff...

print "Stock imported"

def extractPrices(allDetails):
	return extractIndex(allDetails, 6)

def extractIndex(allDetails, index):
	# Date = 0, Open = 1, High = 2, Low = 3,
	# Close = 4, Volume = 5, Adj Close = 6
	# 2004-10-08 138.72 139.68 137.02 137.73 5540300 137.7 
	extracted = []
	for elem in allDetails:
		extracted.append(float(elem[index]))
	return extracted

def multiplyLists(a, b):
	# asserting that the length of a and b are the same...
	# >>> import numpy
# >>> a=numpy.array([0,1,2])
# >>> b=numpy.array([3,4,5])
# >>> a+b

	c = []
	i = 0
	while i < len(a):
		c.append(a[i] * b[i])
		i++
	return c

def percentDifference(list):
	# assuming that the first (0th) element is the newest
	# so diff(0) = latest percent increase = list[0] - list[1] / list[1]
	diff = []
	prev = None
	for elem in list:
		if prev != None:
			diff.append((prev - elem) / elem)
		prev = elem
	return diff

def getDistance(prices):
	print "new distances 1"
	distance = 0
	for index in range(1,len(prices)):
		distance += abs(prices[index] - prices[index-1])
	return distance


def percentageGrowth(prices):
	growthChart = []
	for index in range(1, len(prices)):
		priceDifference = prices[index] - prices[index-1]
		percentDifference = priceDifference / float(prices[index-1])
		growthChart.append(percentDifference)
	return growthChart

def medianPrice(prices):
	pricesCopy = prices[:] # make a clean copy
	pricesCopy.sort()
	midPoint = len(pricesCopy) / 2
	return pricesCopy[midPoint]

def averagePrice(prices):
	total = 0
	for elem in prices:
		total += elem
	return total / len(prices)

# sums the 1st, 1st, 2nd, 3rd, 5th, 8th, 13th, 21st, ... fib(n)th terms
# until you reach a term out of range and then finds the average
def fibonacciAverage(prices):
	if len(prices) > 1:
		total = 2 * prices[0] # the first 2 terms of fibonacci sequence is 11, so 2 * 1
		pointsSummed = 2
		f1 = 1
		f2 = 1
		while (f1 + f2) < len(prices):			
			temp = f1 + f2
			f1 = f2
			f2 = temp
			total += prices[f2]
			pointsSummed += 1
		return total / pointsSummed

#fibonacci averaged over regions...
def smoothFiboAvg(prices):
	if len(prices) > 1:
		total = 2 * prices[0]
		pointsSummed = 2
		regionPoints = 1
		f1 = 1
		f2 = 1
		n = 1
		tempSum = 0
		while n < len(prices):
		       tempSum += prices[n]
		       regionPoints+=1
		total += (tempSum / regionPoints)
		
	       
	       


# Sum up the values, giving less priority to older values
# elem1 + elem2/2 + elem3/3 + elem4/4 ... elemN / N 
# where elem1 is the most current price( i.e. prices[0]) and elemN is the oldest price
def linearRegressiveSum(prices):
	total = 0
	for index in range(len(prices)):
		total += (prices[index] / (index + 1))

	return total


# if i sort it based on the prices, how will the dates appear? what order? how will the be spaced? how are they clustered? 
# can you detect history repeating/mimicking itself like this? how about if we do it on percent gain and loss instead of actual price?
# ...

# what do each of these metrics say about the future of the stock? when should i buy it? when should I sell it? how can i rely on it if each point in 
# time, it tells me to buy or sell at a different point in time...




# goog: august 20 2004 - present

# print ystockquote.get_price('GOOG')

# Get Quotes 01/01/2006 - 01/01/2009
# GOOG = ystockquote.get_historical_prices('GOOG', '20060101', '20090101')


# Create empty lists, quick and dirty
# GOOGOpen = [ ]
# GOOGClose = [ ]
# GOOGHigh = [ ]
# GOOGLow = [ ]
# GOOGAdj = [ ]
# GOOGVolume = [ ]

# Populate lists from downloaded data
# for i in range(1, 755):
#	GOOGDate.append(GOOG[i][0])
#	GOOGOpen.append(GOOG[i][1])
#	GOOGHigh.append(GOOG[i][2])
#	GOOGLow.append(GOOG[i][3])
#	GOOGClose.append(GOOG[i][4])
#	GOOGVolume.append(GOOG[i][5])
 #	GOOGAdj.append(GOOG[i][6])

#plt.plot(GOOGAdj)
#plt.title("Google Adjusted Close")
#plt.ylabel(r"GOOG Closing Price ($USD)", fontsize = 12)
#plt.xlabel(r"Date", fontsize = 12)
#plt.grid(True)

#plt.plot([1,2,3,4], [1,4,9,16], 'ro')
#plt.plot([1,2,3,4], [1,8,2,4])
#plt.axis([0, 6, 0, 20])
#plt.ylabel('some y numbers')
#plt.xlabel('some x numbers')
#plt.show()
