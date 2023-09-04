/*Script to extract multi-labelling in different channels.
 * 
 *
 * Original script from Pete Bankhead script: https://qupath.readthedocs.io/en/0.4/docs/advanced/exporting_annotations.html#full-labeled-image
 * Just changed some parameters.
 * 
 * Run the script when the annotation is done. The annotation must have a class. 
 * If you want more than one class, just set .addLabel("class_name",n), where n is the value in the 8-bit image generated. 
 * Default output format: ome.tif
 *
 * @author: Isaac Vieco-Martí
 * 
 */
 
 
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'labelling')
mkdirs(pathOutput)

def downsample = 1

// Get the main QuPath data structures
def imageData = getCurrentImageData()
def hierarchy = imageData.getHierarchy()
def server = imageData.getServer()


def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def path = buildFilePath(pathOutput, name + ".ome.tif")


// Create an ImageServer where the pixels are derived from annotations
def labelServer = new LabeledImageServer.Builder(imageData)
  .backgroundLabel(0, ColorTools.BLACK) // Specify background label (usually 0 or 255)
  .downsample(downsample)    // Choose server resolution; this should match the resolution at which tiles are exported
  .addLabel('Tumor', 1)      // Choose output labels (the order matters!)
  .multichannelOutput(true) // If true, each label refers to the channel of a multichannel binary image (required for multiclass probability)
  .build()

// Write the image
writeImage(labelServer, path)


print("Labelling exportado")


