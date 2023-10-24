package com.mingyisoft.javademo.collection.list;

/**
 * 链表
 */
public class LIstNodeDemo1 {
	public static void main(String[] argus) {
		
		try {
			
		} catch(Exception ex) {
			
		}
		
		ListNode a1 = new ListNode(2);
		ListNode b1 = new ListNode(4);
		ListNode c1 = new ListNode(3);

		a1.next = b1;
		b1.next = c1;

		ListNode a2 = new ListNode(5);
		ListNode b2 = new ListNode(6);
		ListNode c2 = new ListNode(4);
		a2.next = b2;
		b2.next = c2;

		ListNode returnNode = addTwoNumbers(a1, a2);

		do {
			System.out.print(returnNode.val + "--->");
			returnNode = returnNode.next;
		} while (returnNode != null);
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode dummy = new ListNode(0);
		ListNode current = dummy;
		ListNode temp1 = l1;
		ListNode temp2 = l2;
		int carry = 0;

		do {
			int cal = (temp1 != null ? temp1.val : 0) + (temp2 != null ? temp2.val : 0) + carry;
			carry = 0;
			if(cal>=10) {
				carry = 1;
				cal = cal - 10;
			}
			current.next = new ListNode(cal);

			temp1 = (temp1 != null ? temp1.next : null);
			temp2 = (temp2 != null ? temp2.next : null);
			if(temp1!=null || temp2!=null || carry!=0) {
				current = current.next;
			}
			
		} while (temp1!=null || temp2!=null || carry!=0);

		return dummy.next;
	}
}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}