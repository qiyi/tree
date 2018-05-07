package org.isouth.tree;

import java.util.*;

public class BinaryTree<T> implements Tree<T> {
    private static class Node<T> {
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        int key;
        T value;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"key\":" + key);
            sb.append(", \"value\":" + value);
            sb.append(", \"left\":" + (left == null ? "null" : left.toString()));
            sb.append(", \"right\":" + (right == null ? "null" : right.toString()));
            sb.append("}");
            return sb.toString();
        }

    }

    private <T> Node<T> newNode(Node root, int key, T value) {
        Node<T> node = new Node<T>();
        node.parent = root;
        node.key = key;
        node.value = value;
        return node;
    }

    private Node<T> root;

    @Override
    public void add(int key, T value) {
        if (root == null) {
            root = newNode(null, key, value);
            return;
        }
        add(root, key, value);
    }

    private void add(Node<T> node, int key, T value) {
        if (node.key == key) {
            node.value = value;
        } else if (node.key > key) {
            if (node.left == null) {
                node.left = newNode(node, key, value);
                return;
            } else {
                add(node.left, key, value);
            }
        } else {
            if (node.right == null) {
                node.right = newNode(node, key, value);
                return;
            } else {
                add(node.right, key, value);
            }
        }
    }

    @Override
    public T get(int key) {
        if (root == null) {
            return null;
        }
        return get(root, key);
    }

    private T get(Node<T> node, int key) {
        if (node.key == key) {
            return node.value;
        } else if (node.key > key) {
            if (node.left == null) {
                return null;
            } else {
                return get(node.left, key);
            }
        } else {
            if (node.right == null) {
                return null;
            } else {
                return get(node.right, key);
            }
        }
    }


    @Override
    public T delete(int key) {
        if (root == null) {
            return null;
        }
        return delete(root, key);
    }

    private Node<T> getMin(Node<T> node) {
        if (node.left == null) {
            return node;
        }
        return getMin(node.left);
    }

    private T delete(Node<T> node, int key) {
        if (node.key == key) {
            if (node.left == null && node.right == null) {
                // 叶子节点
                if (node.parent == null) {
                    // 根节点
                    root = null;
                } else if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            } else if (node.left != null && node.right != null) {
                // 两边子树都存在
                // 找到右子树的最小节点，把左子节点挂上去作为他的左子节点
                Node<T> minNode = getMin(node.right);
                minNode.left = node.left;
                node.left.parent = minNode;
                if (node.parent == null) {
                    // 根节点, 那么右子节点先作为 root
                    root = node.right;
                    root.parent = null;
                } else if (node.parent.left == node) {
                    node.parent.left = node.right;
                    node.right.parent = node.parent;
                } else {
                    node.parent.right = node.right;
                    node.right.parent = node.parent;
                }
            } else {
                // 有一个子树
                if (node.parent == null) {
                    // 根节点
                    root = node.left == null ? node.right : node.left;
                    root.parent = null;
                } else if (node.parent.left == node) {
                    node.parent.left = node.left == null ? node.right : node.left;
                    node.parent.left.parent = node.parent;
                } else {
                    node.parent.right = node.left == null ? node.right : node.left;
                    node.parent.right.parent = node.parent;
                }
            }
            return node.value;
        } else if (node.key > key) {
            if (node.left == null) {
                return null;
            } else {
                return delete(node.left, key);
            }
        } else {
            if (node.right == null) {
                return null;
            } else {
                return delete(node.right, key);
            }
        }
    }


    @Override
    public List<T> rangeSearch(int min, int max) {
        if (root == null) {
            return Collections.emptyList();
        }
        // 找到左边最小的节点, 然后中序遍历过去
        Node<T> node = root;
        while (node.left != null && node.left.key >= min) {
            node = node.left;
        }

        Set<Node<T>> visited = new HashSet<>();
        List<T> values = new ArrayList<>();
        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node<T> n = stack.peek();
            visited.add(n);
            if (n.left != null && n.left.key >= min && !visited.contains(n.left)) {
                stack.push(n.left);
                continue;
            }
            values.add(n.value);
            stack.pop();
            if (n.right != null && n.right.key <= max) {
                stack.push(n.right);
                continue;
            }
            if (n.parent != null && n.parent.key <= max && !visited.contains(n.parent)) {
                stack.push(n.parent);
                continue;
            }
        }
        return values;
    }

    @Override
    public String toString() {
        return root == null ? "null" : root.toString();
    }


}
