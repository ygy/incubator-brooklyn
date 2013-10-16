package brooklyn.util.task.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brooklyn.location.basic.SshMachineLocation;
import brooklyn.util.collections.MutableMap;
import brooklyn.util.config.ConfigBag;
import brooklyn.util.text.Strings;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ProcessTaskStub {
    
    protected final List<String> commands = new ArrayList<String>();
    /** null for localhost */
    protected SshMachineLocation machine;
    
    // config data
    protected String summary;
    protected final ConfigBag config = ConfigBag.newInstance();
    
    public static enum ScriptReturnType { CUSTOM, EXIT_CODE, STDOUT_STRING, STDOUT_BYTES, STDERR_STRING, STDERR_BYTES }
    protected Function<ProcessTaskWrapper<?>, ?> returnResultTransformation = null;
    protected ScriptReturnType returnType = ScriptReturnType.EXIT_CODE;
    
    protected Boolean runAsScript = null;
    protected boolean runAsRoot = false;
    protected Boolean requireExitCodeZero = null;
    protected String extraErrorMessage = null;
    protected Map<String,String> shellEnvironment = new MutableMap<String, String>();
    protected final List<Function<ProcessTaskWrapper<?>, Void>> completionListeners = new ArrayList<Function<ProcessTaskWrapper<?>,Void>>();

    public ProcessTaskStub() {}
    
    protected ProcessTaskStub(ProcessTaskStub source) {
        commands.addAll(source.getCommands());
        machine = source.getMachine();
        summary = source.getSummary();
        config.copy(source.getConfig());
        returnResultTransformation = source.returnResultTransformation;
        returnType = source.returnType;
        runAsScript = source.runAsScript;
        runAsRoot = source.runAsRoot;
        requireExitCodeZero = source.requireExitCodeZero;
        extraErrorMessage = source.extraErrorMessage;
        shellEnvironment.putAll(source.getShellEnvironment());
        completionListeners.addAll(source.getCompletionListeners());
    }

    public String getSummary() {
        if (summary!=null) return summary;
        return Strings.join(commands, " ; ");
    }
    
    /** null for localhost */
    public SshMachineLocation getMachine() {
        return machine;
    }
    
    public Map<String, String> getShellEnvironment() {
        return ImmutableMap.copyOf(shellEnvironment);
    }
 
    @Override
    public String toString() {
        return super.toString()+"["+getSummary()+"]";
    }

    public List<String> getCommands() {
        return ImmutableList.copyOf(commands);
    }
 
    public List<Function<ProcessTaskWrapper<?>, Void>> getCompletionListeners() {
        return completionListeners;
    }

    protected ConfigBag getConfig() { return config; }
    
}