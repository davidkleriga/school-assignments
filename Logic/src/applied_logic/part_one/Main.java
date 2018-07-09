package applied_logic.part_one;

import java.util.InputMismatchException;
import java.util.Scanner;

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

        String ipv4Address = "192.168.0.0";
        String subnetMaskAddress = "255.255.255.248";

//        String ipv4Address = Prompt.GetPromptValueSynchronous(PROMPT_MESSAGES[0], PROMPT_ERROR_MESSAGE, IP_ADDRESS_PATTERN );
//        String subnetMaskAddress =Prompt.GetPromptValueSynchronous(PROMPT_MESSAGES[1], PROMPT_ERROR_MESSAGE, IP_ADDRESS_PATTERN );

        System.out.println("ipv4Address: " + ipv4Address);
        System.out.println("subnetMaskAddress: " + subnetMaskAddress);
        //Either accept or reject. Print
//
//        int MAX_SUBNET_VALUE = Integer.parseInt("11111111", 2);
//        String BINARY_VALUE = Integer.toBinaryString(127);
//        int BACK_BINARY_VALUE = Integer.parseInt(BINARY_VALUE, 2);

        boolean isValidSubnet = true;

        String[] subnetParts = subnetMaskAddress.split("\\.");

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


//        System.out.println(MAX_SUBNET_VALUE);
//        System.out.println(BINARY_VALUE);
//        System.out.println("^M^^B");
//        System.out.println(BACK_BINARY_VALUE + " bbv");

        System.out.println("subnet mask: " + subnetMaskAddress + " validity is: " + isValidSubnet);

        String secondIpv4Address = Prompt.GetPromptValueSynchronous(PROMPT_MESSAGES[2], PROMPT_ERROR_MESSAGE, IP_ADDRESS_PATTERN );
        System.out.println("secondIpv4Address: " + secondIpv4Address);

        int ipv4AddressAndSubmask = Integer.parseInt(ipv4Address, 2) & Integer.parseInt(subnetMaskAddress, 2);
        System.out.println("ipv4AddressAndSubmask: " + ipv4AddressAndSubmask);
        int secondIpv4AddressAndSubmask = Integer.parseInt(secondIpv4Address, 2) & Integer.parseInt(subnetMaskAddress, 2);
        System.out.println("secondIpv4AddressAndSubmask: " + secondIpv4AddressAndSubmask);

        System.out.println("Are they on the same network? " + (ipv4AddressAndSubmask == secondIpv4AddressAndSubmask ));
        //Enter a second IP address and check if it is in the same network (submask match)

    }
}
