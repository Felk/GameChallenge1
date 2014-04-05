package de.felk.gamepp1.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import de.felk.gamepp1.entities.Vector;

public class Testsound {

	int buffer;
	int source;
	Vector position = new Vector(0, 0);
	Vector velocity = new Vector(0, 0);
	private boolean looping = true;
	private int state;
	private float offset;
	private float size;
	public float crash = 2f;

	public Testsound(File file) throws LWJGLException, IOException {
		this(file, new Vector(0, 0), false);
	}

	public Testsound(File file, Vector pos, boolean looping) throws LWJGLException, IOException {
		// buffer und sources an OpenAL weiterleiten
		buffer = alGenBuffers();
		source = alGenSources();

		// Datei in den Buffer laden
		String format = getExtension(file);
		switch (format) {
		case "wav":
			setWaveFile(file);
			break;
		default:
			setWaveFile(file);
		}
		
		// Source festlegen
		setSource(pos, new Vector(0, 0));

		// Listener positionieren
		setListener(new Vector(0, 0), new Vector(0, 0), new Vector(0, 0));
		
		// OpenAL mitteilen, ob der Sound wiederholt werden soll
		this.looping = looping;
		updateLooping();
		setGain(1);
		setPitch(1);
		//alDopplerFactor(100000);
		//alDopplerVelocity(1000);
	}

	private void setWaveFile(File file) throws FileNotFoundException {
		WaveData waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(file)));
		setBuffer(waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
	}
	
	private void setBuffer(int format, ByteBuffer data, int samplerate) {
		alBufferData(buffer, format, data, samplerate);
		this.size = AL10.alGetBufferi(buffer, AL_SIZE);
	}

	private void setSource(Vector position, Vector velocity) {
		//if (AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;
		alSourcei(source, AL_BUFFER, buffer);
		setPitch(1.5f);
		setGain(1.0f);
		alSource3f(source, AL_POSITION, position.getX(), position.getY(), -1);
		alSource3f(source, AL_VELOCITY, velocity.getX(), velocity.getY(), -1);

	}
	
	public void setPitch(float pitch) {
		alSourcef(source, AL_PITCH, pitch);
	}
	
	public void setGain(float gain) {
		alSourcef(source, AL_GAIN, gain);
	}

	public static void setListener(Vector position, Vector velocity, Vector orientation) {
		alListener3f(AL_POSITION, position.getX(), position.getY(), -1);
		alListener3f(AL_VELOCITY, velocity.getX(), velocity.getY(), -1);
		alListener3f(AL_ORIENTATION, orientation.getX(), orientation.getY(), -1);
	}

	public void play() {
		alSourcePlay(source);
	}

	public void stop() {
		alSourceStop(source);
	}

	public void pause() {
		alSourcePause(source);
	}

	public void destroy() {
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}

	public boolean isPaused() {
		return (this.state == AL_PAUSED);
	}

	public boolean isPlaying() {
		return (this.state == AL_PLAYING);
	}

	public boolean isLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
		updateLooping();
	}

	public void updateLooping() {
		if (looping) {
			alSourcei(source, AL_LOOPING, AL_TRUE);
		} else {
			alSourcei(source, AL_LOOPING, AL_FALSE);
		}
	}
	
	public void update() {
		this.state = alGetSourcei(source, AL_SOURCE_STATE);
		this.offset = (float) alGetSourcei(source, AL_BYTE_OFFSET) / size;
		if (crash < 1) {
			setOffset(crash);
		}
		//System.out.println(offset);
	}
	
	public boolean isStopped() {
		return (this.state == AL_STOPPED);
	}

	public int getState() {
		return this.state;
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		alSourcei(source, AL_BYTE_OFFSET, (int) (offset*size));
	}
	
	public void crash() {
		crash = getOffset();
	}
	
	public void uncrash() {
		crash = 2;
	}
	
	public String getExtension(File file) {
		String[] parts = file.getName().split("\\.");
		if (parts.length <= 1) return null;
		return parts[parts.length-1].toLowerCase();
	}
	
}
