/*
 * Script to export ROIs from an stack composite. 
 * Just change the downsample if needed for later registration. 
 * 
 * @author Isaac Vieco-Martí
 * 
 */
//Define downsample if needed

def downsample = 4


//Define destination folder
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'ROIs_2')
mkdirs(pathOutput)

//image name
def name = getCurrentImageNameWithoutExtension()

// for Loop

for( annotation in getAnnotationObjects()){
    def server = getCurrentServer()
    def roi = annotation.getROI()
    def classification = annotation.getPathClass()
    def requestROI = RegionRequest.createInstance(server.getPath(), downsample, roi)
    
    def outputPath= buildFilePath(pathOutput,  name  +'_'+ classification+ '.tif')
    writeImageRegion(server, requestROI, outputPath)
    }

print("ROI exported")
