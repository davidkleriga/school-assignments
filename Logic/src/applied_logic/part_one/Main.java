package applied_logic.part_one;

import java.util.ArrayList;
import java.util.List;

import applied_logic.utility.*;


public class Main {
    public static final String IP_ADDRESS_PATTERN = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    public static final String WELCOME_MESSAGE = "Applied Propositional Logic: Part 1. \nSubnet Mask Utility";
    public static final String[] PROMPT_MESSAGES = new String[] {
            "Please enter IPv4 Network Address in following format: 192.168.0.0",
            "Please enter a subnet mask in following format: 255.255.255.248",
            "Please enter a second IP Address to see if it resides within the same network as the first"
    };
    public static final String PROMPT_ERROR_MESSAGE = "Please try again and verify formatting";

    public static void main(String[] args) {

        System.out.println(WELCOME_MESSAGE);

//        String ipv4Address = "192.168.0.0";
//        String subnetMaskAddress = "255.255.255.248";

        String ipv4Address = Prompt.GetPromptValueSynchronous(PROMPT_MESSAGES[0], PROMPT_ERROR_MESSAGE, IP_ADDRESS_PATTERN );
        String subnetMaskAddress =Prompt.GetPromptValueSynchronous(PROMPT_MESSAGES[1], PROMPT_ERROR_MESSAGE, IP_ADDRESS_PATTERN );


        boolean isValidSubnet = true;

        String[] subnetParts = subnetMaskAddress.split("\\.");

        //validate subnet

        if (subnetParts.length != 4) {
            isValidSubnet = false;
        } else {
            for ( int i = 0 ; i < subnetParts.length; i++) {
                int numberValue = Integer.parseInt(subnetParts[i]);
                String binaryString = Integer.toBinaryString(numberValue);
                char bitValueToCheckFor = '1';
                for ( int j = 0; j < binaryString.length(); j++ ) {
                    char indexedChar = binaryString.charAt(j);
                    if ( indexedChar == bitValueToCheckFor ) {
                        //do nothing, keep going to the end
                    } else if (bitValueToCheckFor == '1' && indexedChar != '1') {
                        //no more 1s check for 0s
                        bitValueToCheckFor = '0';
                    } else if (bitValueToCheckFor == '0' && indexedChar != '0') {
                        isValidSubnet = false;
                        System.out.println("invalid subnet mask at indexes (Section index: " + i + ", Bit index: " + j + ") " + binaryString);
                        break;
                    }
                }
            }
        }

        System.out.println("subnet mask: " + subnetMaskAddress + " validity is: " + isValidSubnet);

        String secondIpv4Address = Prompt.GetPromptValueSynchronous(PROMPT_MESSAGES[2], PROMPT_ERROR_MESSAGE, IP_ADDRESS_PATTERN );
        System.out.println("secondIpv4Address: " + secondIpv4Address);

        String[] ipv4Parts = ipv4Address.split("\\.");
        String[] secondIpv4Parts = secondIpv4Address.split("\\.");

        List<String> ipv4MaskedBitChunks = new ArrayList<>();
        List<String> secondIPv4MaskedBitChunks = new ArrayList<>();

        for ( int i = 0; i < ipv4Parts.length; i++) {
            int numberValue = Integer.parseInt(ipv4Parts[i]);
            String binaryStringValue = Integer.toBinaryString(numberValue);
            int binaryValue = Integer.parseInt(binaryStringValue, 2);

            int submaskBits = Integer.parseInt(Integer.toBinaryString(Integer.parseInt(subnetParts[i])), 2);
            ipv4MaskedBitChunks.add(Integer.toBinaryString(binaryValue & submaskBits));
        }

        for ( int i = 0; i < secondIpv4Parts.length; i++) {
            int numberValue = Integer.parseInt(secondIpv4Parts[i]);
            String binaryStringValue = Integer.toBinaryString(numberValue);
            int binaryValue = Integer.parseInt(binaryStringValue, 2);

            int submaskBits = Integer.parseInt(Integer.toBinaryString(Integer.parseInt(subnetParts[i])), 2);
            secondIPv4MaskedBitChunks.add(Integer.toBinaryString(binaryValue & submaskBits));
        }

        boolean residesOnSameNetwork = true;
        for ( int i = 0; i < ipv4MaskedBitChunks.size(); i++) {
            if ( !ipv4MaskedBitChunks.get(i).equals(secondIPv4MaskedBitChunks.get(i)) ) {
                residesOnSameNetwork = false;
                break;
            }
        }

        System.out.println("ipv4AddressAndSubmask: " + ipv4Address);
        System.out.println("secondIpv4Address: " + secondIpv4Address);

        System.out.println("IP chunks #1: " + ipv4MaskedBitChunks);
        System.out.println("IP chunks #2: " + secondIPv4MaskedBitChunks);
//
        System.out.println("Are they on the same network? " + (residesOnSameNetwork ));
        //Enter a second IP address and check if it is in the same network (submask match)

    }
}
