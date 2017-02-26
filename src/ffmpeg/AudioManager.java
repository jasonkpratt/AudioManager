package ffmpeg;

import java.io.IOException;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class AudioManager {

	public void createAudio(String path){
		String inputFile=path;
		String ffmpegPath="C:\\Users\\jason\\Documents\\ffmpeg\\bin\\ffmpeg";
		String ffprobePath="C:\\Users\\jason\\Documents\\ffmpeg\\bin\\ffprobe";
		FFmpeg ffmpeg = null;
		try {
			ffmpeg = new FFmpeg(ffmpegPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FFprobe ffprobe = null;
		try {
			ffprobe = new FFprobe(ffprobePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String outputFile=inputFile.replaceAll(".mp4", ".mp3");
		FFmpegBuilder builder = new FFmpegBuilder()

		  .setInput(inputFile)     // Filename, or a FFmpegProbeResult
		  //.overrideOutputFiles(true) // Override the output if it exists

		  .addOutput(outputFile)   // Filename for the destination
		    //.setFormat("mp3")        // Format is inferred from filename, or can be set
		   // .setTargetSize(250_000)  // Aim for a 250KB file

		   // .disableSubtitle()       // No subtiles

		  //  .setAudioChannels(1)         // Mono audio
		 //   .setAudioCodec("aac")        // using the aac codec
		  //  .setAudioSampleRate(48_000)  // at 48KHz
		 //   .setAudioBitRate(32768)      // at 32 kbit/s

		 //   .setVideoCodec("libx264")     // Video using x264
		////    .setVideoFrameRate(24, 1)     // at 24 frames per second
	//	    .setVideoResolution(640, 480) // at 640x480 resolution

		//    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
		    .done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		// Run a one-pass encode
		executor.createJob(builder).run();
		System.out.println("Completed");
	}
	public static void main(String [] args){
		AudioManager mananger=new AudioManager();
		mananger.createAudio("C:\\Users\\jason\\Music\\temp\\Green Grass and High Tides.audio.mp4");
	}
}
