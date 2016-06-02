package cn.edu.sau.model;

/**
 * Õº È¿‡
 * */
public class Book {
	private int id;
	private String bookName;
	private String bookAuthor;
	private float bookPrice;
	private String bookDesc;
	private int bookTypeId;
	private String bookTypeName;

	public Book() {
		super();
		this.id = -1;
		this.bookName = "";
		this.bookAuthor = "";
		this.bookPrice = 0;
		this.bookDesc = "";
		this.bookTypeId = -1;
	}

	public Book(int id, String bookName) {
		super();
		this.id = id;
		this.bookName = bookName;
	}

	public Book(int id, String bookName, String bookAuthor, float bookPrice,
			String bookDesc, int bookTypeId) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPrice = bookPrice;
		this.bookDesc = bookDesc;
		this.bookTypeId = bookTypeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	public int getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(int bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}
}
