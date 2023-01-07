package com.cosmos.service.impl;

import com.cosmos.entity.Order_Detail;
import com.cosmos.entity.Orders;
import com.cosmos.entity.Product;
import com.cosmos.model.GetDateFromAndTo;
import com.cosmos.model.ProductResponse;
import com.cosmos.repository.OrderDetailRepository;
import com.cosmos.repository.OrderRepository;
import com.cosmos.repository.ProductRepository;
import com.cosmos.service.ReportService;
import com.cosmos.util.DateUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.Map.Entry;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public void getDailyRevenueReport(String staffName, GetDateFromAndTo date) {
    try {
      Document document = new Document(PageSize.A3.rotate());
      String pdfFilePath = System.getProperty("user.dir") + "\\file.pdf";
      OutputStream fos = new FileOutputStream(new File(pdfFilePath));
      PdfWriter.getInstance(document, fos);
      document.open();
      SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat ft2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      SimpleDateFormat ft3 = new SimpleDateFormat("dd-MM-yyyy");

      Date from = Date.from(
              DateUtils.stringToZonedDateTime(date.getFrom().toString())
                      .atStartOfDay(ZoneId.systemDefault()).toInstant());

      Date to = Date.from(
              DateUtils.stringToZonedDateTime(date.getTo().toString())
                      .atStartOfDay(ZoneId.systemDefault()).toInstant());
      List<Orders> orders = orderRepository
              .findAllByOrderCreatedayBetween(from, to);

      //Set tiêu đề phía trê
      //Create Image object
//      Image image = Image.getInstance("perfurme.png");
//      image.scaleToFit(330, 200);
//      image.setAbsolutePosition(-60, 620);
      // tiêu đề
      Paragraph pTieuDe = new Paragraph(
              "       Name: PERFUME SHOP \n       Address: 97 Man Thien, Hiep Phú, 9 District\n       Tax code: 092474611111",
              font16);
      //Add content to the document using Image object.
      Paragraph pTemp = new Paragraph(" ",
              font1);
      Paragraph p = new Paragraph(" DAILY REVENUE STATEMENT",
              font1);
      Paragraph pStaff = new Paragraph(" Staff: " + staffName,
              font15);
      Paragraph pYear = new Paragraph("Created on: " + ft2.format(new Date()),
              font15);
      Paragraph pDate = new Paragraph(
              " From:  " + ft3.format(from) + "           To:  " + ft3.format(to),
              font16);
      List<Product> products = productRepository.findAll();
      int numberOfColumns = products.size() + 1;
      HashMap<Integer, Float> list = new HashMap<>();
      Paragraph paragraph = new Paragraph();
      PdfPTable table = new PdfPTable(numberOfColumns);
      Float total = 0f;
      int count = 0;
      for (Product product : products) {
        list.put(product.getId(), 0F);
      }

      for (Integer key : list.keySet()) {
        Product product = productRepository.getById(key);
        if (count == 0) {
          insertHeader(table, " ", Element.ALIGN_CENTER, 1, font14);
          insertHeader(table, product.getName() + " (" + product.getCapacity() + ") ",
                  Element.ALIGN_CENTER, 1, font14);
        } else {
          insertHeader(table, product.getName() + " (" + product.getCapacity() + ") ",
                  Element.ALIGN_CENTER, 1, font14);
        }
        count++;
      }

      List<Date> dates = DateUtils.getDatesFromTo(ft.format(from), ft.format(to));
      for (Date date1 : dates) {

        insertCell(table, ft.format(date1),
                Element.ALIGN_CENTER, 1, font14);
        List<Orders> dailyOrder = orderRepository.findAllByOrderCreateday(date1);
        if (dailyOrder.size() > 0) {
          for (Orders order : dailyOrder) {
            for (Order_Detail order_detail : order.getOrderDetailList()) {
              Integer key = order_detail.getProduct().getId();
              list.put(key, list.get(key) +
                      (order_detail.getNumber() * order_detail.getPrice()));
              total += order_detail.getNumber() * order_detail.getPrice();
            }
          }
        }
        for (Integer key : list.keySet()) {
          insertCell(table, formatCurrency(list.get(key)),
                  Element.ALIGN_CENTER, 1, font14);
        }
        list = resetHashMapIF(list);
      }
      Paragraph pTotal = new Paragraph(" Total: " + formatCurrency(total),
              font16);
      // set table width a percentage of the page width
      table.setWidthPercentage(80);

      pDate.setAlignment(Paragraph.ALIGN_CENTER);
      pStaff.setAlignment(Paragraph.ALIGN_CENTER);
      pYear.setAlignment(Paragraph.ALIGN_CENTER);
      p.setAlignment(Paragraph.ALIGN_CENTER);
      pTotal.setAlignment(Paragraph.ALIGN_RIGHT);

      //add the PDF table to the paragraph
      paragraph.add(table);
      // add the paragraph to the document
//      document.add(image);
      document.add(pTieuDe);
      document.add(p);
      document.add(pYear);
      document.add(pStaff);
      document.add(pTemp);
      document.add(pDate);
      document.add(pTemp);
      document.add(paragraph);
      document.add(pTemp);
      document.add(pTotal);
      document.close();
      runPDF(pdfFilePath);
    } catch (IOException exception) {
      System.out.print(exception.getMessage());
    } catch (Exception t) {
      System.out.print(t.getMessage());
    }
  }

  public void stockReport(String staffName) {
    try {
      Document document = new Document(PageSize.A3.rotate());
      String pdfFilePath = System.getProperty("user.dir") + "\\file.pdf";
      OutputStream fos = new FileOutputStream(new File(pdfFilePath));
      PdfWriter.getInstance(document, fos);
      document.open();
      SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat ft2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      SimpleDateFormat ft3 = new SimpleDateFormat("dd-MM-yyyy");

      //Set tiêu đề phía trê
      //Create Image object
//      Image image = Image.getInstance("perfurme.png");
//      image.scaleToFit(330, 200);
//      image.setAbsolutePosition(-60, 620);
      // tiêu đề
      Paragraph pTieuDe = new Paragraph(
              "       Name: PERFUME SHOP \n       Address: 97 Man Thien, Hiep Phú, 9 District\n       Tax code: 092474611111",
              font16);
      //Add content to the document using Image object.
      Paragraph pTemp = new Paragraph(" ",
              font1);
      Paragraph p = new Paragraph(" QUANTITY OF S STOCK STATEMENT",
              font1);
      Paragraph pStaff = new Paragraph(" Staff: " + staffName,
              font15);
      Paragraph pYear = new Paragraph("Created on: " + ft2.format(new Date()),
              font15);
      List<Product> products = productRepository.findAll();
      int numberOfColumns = products.size();
      HashMap<Integer, Integer> list = new HashMap<>();
      Paragraph paragraph = new Paragraph();
      PdfPTable table = new PdfPTable(3);

      insertHeader(table, "ID", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "NAME", Element.ALIGN_CENTER, 1, font14);
      //insertHeader(table, "CAPACITY", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "QUANTITY", Element.ALIGN_CENTER, 1, font14);
      for (Product product : products) {
        insertCell(table, String.valueOf(product.getId()), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, product.getName(), Element.ALIGN_CENTER, 1,
                font14);
//        insertCell(table, product.getCapacity().toString(), Element.ALIGN_CENTER, 1,
//                font14);
        insertCell(table, String.valueOf(product.getNumber()), Element.ALIGN_CENTER, 1,
                font14);
      }

      // set table width a percentage of the page width
      table.setWidthPercentage(80);
      pStaff.setAlignment(Paragraph.ALIGN_CENTER);
      pYear.setAlignment(Paragraph.ALIGN_CENTER);
      p.setAlignment(Paragraph.ALIGN_CENTER);

      //add the PDF table to the paragraph
      paragraph.add(table);
      // add the paragraph to the document
//      document.add(image);
      document.add(pTieuDe);
      document.add(p);
      document.add(pYear);
      document.add(pStaff);
      document.add(pTemp);
      document.add(pTemp);
      document.add(paragraph);
      document.add(pTemp);
      document.close();

      runPDF(pdfFilePath);
    } catch (IOException exception) {
      System.out.print(exception.getMessage());
    } catch (Exception t) {
      System.out.print(t.getMessage());
    }
  }

  @Override
  public void getProfitReport(String staffName) {
    try {

      Document document = new Document(PageSize.A3.rotate());
      String pdfFilePath = System.getProperty("user.dir") + "\\file.pdf";
      OutputStream fos = new FileOutputStream(new File(pdfFilePath));
      PdfWriter.getInstance(document, fos);

      document.open();
      SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat ft2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      SimpleDateFormat ft3 = new SimpleDateFormat("dd-MM-yyyy");
//      Image image = Image.getInstance("perfurme.png");
//      image.scaleToFit(330, 200);
//      image.setAbsolutePosition(-60, 620);
      // tiêu đề
      Paragraph pTieuDe = new Paragraph(
              "       Name: Shoekick Shop \n       Address: 97 Man Thien, Hiep Phú, 9 District\n       Tax code: 092474611111",
              font16);
      //Add content to the document using Image object.
      Paragraph pTemp = new Paragraph(" ",
              font1);
      Paragraph p = new Paragraph(" PROFIT REPORT STATEMENT",
              font1);
      Paragraph pStaff = new Paragraph(" Staff: " + staffName,
              font15);
      Paragraph pYear = new Paragraph("Created on: " + ft2.format(new Date()),
              font15);

      Paragraph paragraph = new Paragraph();
      PdfPTable table = new PdfPTable(5);

      insertHeader(table, "ID", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "NAME", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "NUMBER", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "ENTER PRICE", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "PROFIT", Element.ALIGN_CENTER, 1, font14);


      List<Orders> orders = orderRepository
              .findAllByStatus();
      List<Product> products = productRepository.findAll();
      HashMap<Integer, Integer> list = new HashMap<>();
      HashMap<Integer, Float> price = new HashMap<>();
      for (Product product : products) {
        list.put(product.getId(), 0);
        price.put(product.getId(), 0.0F);
      }

      for (Orders order : orders) {
        List<Order_Detail> orderDetailList = orderDetailRepository.findAllByOrderId(order.getId());
        for (Order_Detail order_detail : orderDetailList) {
          for (Integer key : list.keySet()) {
            if (key.equals(order_detail.getProduct().getId())) {
              list.put(key, list.get(key) + order_detail.getNumber());
              price.put(key, price.get(key) + (order_detail.getNumber() * order_detail.getPrice()));
            }
          }
        }
      }
      float total = 0;
      for (Integer key : list.keySet()) {
        Product product = productRepository.findById(key)
                .orElseThrow(() -> new NotFoundException("product-not-found"));
        insertCell(table, String.valueOf(product.getId()), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, product.getName(), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, list.get(key).toString(), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, String.valueOf(product.getCost()), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, String.valueOf(price.get(key) - (list.get(key) * product.getCost())), Element.ALIGN_CENTER, 1,
                font14);
        total +=price.get(key) - (list.get(key) * product.getCost());
      }
      Paragraph pTotal = new Paragraph(" Total: " + formatCurrency(total),
              font16);
      table.setWidthPercentage(80);


      pStaff.setAlignment(Paragraph.ALIGN_CENTER);
      pYear.setAlignment(Paragraph.ALIGN_CENTER);
      p.setAlignment(Paragraph.ALIGN_CENTER);
      pTotal.setAlignment(Paragraph.ALIGN_RIGHT);
      //add the PDF table to the paragraph
      paragraph.add(table);
      // add the paragraph to the document
//      document.add(image);
      document.add(pTieuDe);
      document.add(p);
      document.add(pYear);
      document.add(pStaff);
      document.add(pTemp);
      document.add(pTemp);
      document.add(paragraph);
      document.add(pTemp);
      document.add(pTotal);
      document.close();
      runPDF(pdfFilePath);

    } catch (IOException exception) {
      System.out.print(exception.getMessage());
    } catch (Exception t) {
      System.out.print(t.getMessage());
    }


  }


//  public void getRankReport(String staffName, GetDateFromAndTo date) {
//    try {
//      Document document = new Document(PageSize.A3.rotate());
//      String pdfFilePath = System.getProperty("user.dir") + "\\file.pdf";
//      OutputStream fos = new FileOutputStream(new File(pdfFilePath));
//      PdfWriter.getInstance(document, fos);
//
//      document.open();
//      SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//      SimpleDateFormat ft2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//      SimpleDateFormat ft3 = new SimpleDateFormat("dd-MM-yyyy");
//
//      Date from = Date.from(
//              DateUtils.stringToZonedDateTime(date.getFrom().toString())
//                      .atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//      Date to = Date.from(
//              DateUtils.stringToZonedDateTime(date.getTo().toString())
//                      .atStartOfDay(ZoneId.systemDefault()).toInstant());
//      List<Orders> orders = orderRepository
//              .findAllByOrderCreatedayBetween(from, to);
//      Date comparedFrom = from;
//      Date comparedTo = to;
//      Long totalService = 0L;
//      Long totalBooking = 0L;
//      comparedTo.setHours(12);
//      comparedTo.setMinutes(0);
//      comparedTo.setSeconds(0);
//      comparedFrom.setHours(12);
//      comparedFrom.setMinutes(0);
//      comparedFrom.setSeconds(0);
//      //Set tiêu đề phía trê
//      //Create Image object
//      Image image = Image.getInstance("perfurme.png");
//      image.scaleToFit(330, 200);
//      image.setAbsolutePosition(-60, 620);
//      // tiêu đề
//      Paragraph pTieuDe = new Paragraph(
//              "       Name: PERFUME SHOP \n       Address: 97 Man Thien, Hiep Phú, 9 District\n       Tax code: 092474611111",
//              font16);
//      //Add content to the document using Image object.
//      Paragraph pTemp = new Paragraph(" ",
//              font1);
//      Paragraph p = new Paragraph(" RANKED STATEMENT",
//              font1);
//      Paragraph pStaff = new Paragraph(" Staff: " + staffName,
//              font15);
//      Paragraph pYear = new Paragraph("Created on: " + ft2.format(new Date()),
//              font15);
//      Paragraph pDate = new Paragraph(
//              " From:  " + ft3.format(from) + "           To:  " + ft3.format(to),
//              font16);
//      List<Product> products = productRepository.findAll();
//      int numberOfColumns = products.size();
//      HashMap<Integer, Integer> list = new HashMap<>();
//      Paragraph paragraph = new Paragraph();
//      PdfPTable table = new PdfPTable(4);
//
//      insertHeader(table, "ID", Element.ALIGN_CENTER, 1, font14);
//      insertHeader(table, "NAME", Element.ALIGN_CENTER, 1, font14);
//      insertHeader(table, "CAPACITY", Element.ALIGN_CENTER, 1, font14);
//      insertHeader(table, "QUANTITY OF SELL", Element.ALIGN_CENTER, 1, font14);
//      for (Product product : products) {
//        list.put(product.getId(), 0);
//      }
//
//      for (Orders order : orders) {
//        List<Order_Detail> orderDetailList = orderDetailRepository.findAllByOrderId(order.getId());
//        for (Order_Detail order_detail : orderDetailList) {
//          for (Integer key : list.keySet()) {
//            if (key.equals(order_detail.getProduct().getId())) {
//              list.put(key, list.get(key) + order_detail.getNumber());
//            }
//          }
//        }
//      }
//
//      for (Integer key : list.keySet()) {
//        Product product = productRepository.findById(key)
//                .orElseThrow(() -> new NotFoundException("product-not-found"));
//        insertCell(table, String.valueOf(product.getId()), Element.ALIGN_CENTER, 1,
//                font14);
//        insertCell(table, product.getName(), Element.ALIGN_CENTER, 1,
//                font14);
//        insertCell(table, product.getCapacity().toString(), Element.ALIGN_CENTER, 1,
//                font14);
//        insertCell(table, list.get(key).toString(), Element.ALIGN_CENTER, 1,
//                font14);
//      }
//
//      // set table width a percentage of the page width
//      table.setWidthPercentage(80);
//
//      pDate.setAlignment(Paragraph.ALIGN_CENTER);
//      pStaff.setAlignment(Paragraph.ALIGN_CENTER);
//      pYear.setAlignment(Paragraph.ALIGN_CENTER);
//      p.setAlignment(Paragraph.ALIGN_CENTER);
//
//      //add the PDF table to the paragraph
//      paragraph.add(table);
//      // add the paragraph to the document
//      document.add(image);
//      document.add(pTieuDe);
//      document.add(p);
//      document.add(pYear);
//      document.add(pStaff);
//      document.add(pTemp);
//      document.add(pDate);
//      document.add(pTemp);
//      document.add(paragraph);
//      document.add(pTemp);
//      document.close();
//      runPDF(pdfFilePath);
//    } catch (IOException exception) {
//      System.out.print(exception.getMessage());
//    } catch (Exception t) {
//      System.out.print(t.getMessage());
//    }
//  }

  public static void runPDF(String pdfFilePath) throws Exception {
    Process p = Runtime
            .getRuntime()
            .exec("rundll32 url.dll,FileProtocolHandler " + pdfFilePath);
  }

  public HashMap<Integer, Float> resetHashMapIF(HashMap<Integer, Float> list) {
    for (Integer i : list.keySet()) {
      list.put(i, 0F);
    }
    return list;
  }


  private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

    //create a new cell with the specified Text and Font
    if (text.trim().equals("0")) {
      text = " ";
    } else if (text.trim().equals("0.0")) {
      text = " ";
    }

    PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
    //set the cell alignment
    cell.setHorizontalAlignment(align);
    //set the cell column span in case you want to merge two or more cells
    cell.setColspan(colspan);

    //in case there is no text and you wan to create an empty row
    if (text.trim().equalsIgnoreCase("")) {
      cell.setMinimumHeight(10f);
    }
    //add the call to the table
    table.addCell(cell);

  }

  private void insertHeader(PdfPTable table, String text, int align, int colspan, Font font) {
    //create a new cell with the specified Text and Font
    PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
    //set the cell alignment
    cell.setHorizontalAlignment(align);
    //set the cell column span in case you want to merge two or more cells
    cell.setColspan(colspan);
    cell.setBackgroundColor(new BaseColor(255, 235, 205));
    //in case there is no text and you wan to create an empty row
    if (text.trim().equalsIgnoreCase("")) {
      cell.setMinimumHeight(10f);
    }
    //add the call to the table
    table.addCell(cell);

  }

  public static String formatCurrency(Float currency) {
    String currencyString = String.valueOf(currency.longValue());

    if (currencyString.length() < 4) {
      return currencyString;
    } else {
      int count = 1;
      for (int i = currencyString.length() - 1; i >= 0; i--) {
        if (count == 3 && i != 0) {
          StringBuilder sb = new StringBuilder(currencyString);
          sb.insert(i, ".");
          currencyString = sb.toString();
          count = 1;
          i--;
        }
        count++;
      }
    }
    return currencyString;
  }

  public void getRankReport(String staffName, GetDateFromAndTo date) {
    try {
      Document document = new Document(PageSize.A3.rotate());
      String pdfFilePath = System.getProperty("user.dir") + "\\file.pdf";
      OutputStream fos = new FileOutputStream(new File(pdfFilePath));
      PdfWriter.getInstance(document, fos);

      document.open();
      SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat ft2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      SimpleDateFormat ft3 = new SimpleDateFormat("dd-MM-yyyy");

      Date from = Date.from(
              DateUtils.stringToZonedDateTime(date.getFrom().toString())
                      .atStartOfDay(ZoneId.systemDefault()).toInstant());

      Date to = Date.from(
              DateUtils.stringToZonedDateTime(date.getTo().toString())
                      .atStartOfDay(ZoneId.systemDefault()).toInstant());
      List<Orders> orders = orderRepository
              .findAllByOrderCreatedayBetween(from, to);
      Date comparedFrom = from;
      Date comparedTo = to;
      Long totalService = 0L;
      Long totalBooking = 0L;
      comparedTo.setHours(12);
      comparedTo.setMinutes(0);
      comparedTo.setSeconds(0);
      comparedFrom.setHours(12);
      comparedFrom.setMinutes(0);
      comparedFrom.setSeconds(0);
      //Set tiêu đề phía trê
      //Create Image object
//      Image image = Image.getInstance("perfurme.png");
//      image.scaleToFit(330, 200);
//      image.setAbsolutePosition(-60, 620);
      // tiêu đề
      Paragraph pTieuDe = new Paragraph(
              "       Name: Shoekick Shop \n       Address: 97 Man Thien, Hiep Phú, 9 District\n       Tax code: 092474611111",
              font16);
      //Add content to the document using Image object.
      Paragraph pTemp = new Paragraph(" ",
              font1);
      Paragraph p = new Paragraph(" RANKED STATEMENT",
              font1);
      Paragraph pStaff = new Paragraph(" Staff: " + staffName,
              font15);
      Paragraph pYear = new Paragraph("Created on: " + ft2.format(new Date()),
              font15);
      Paragraph pDate = new Paragraph(
              " From:  " + ft3.format(from) + "           To:  " + ft3.format(to),
              font16);
      List<Product> products = productRepository.findAll();
      int numberOfColumns = products.size();
      HashMap<Integer, Integer> list = new HashMap<>();
      Paragraph paragraph = new Paragraph();
      PdfPTable table = new PdfPTable(4);

      insertHeader(table, "ID", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "NAME", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "SIZE", Element.ALIGN_CENTER, 1, font14);
      insertHeader(table, "QUANTITY OF SELL", Element.ALIGN_CENTER, 1, font14);
      for (Product product : products) {
        list.put(product.getId(), 0);
      }

      for (Orders order : orders) {
        List<Order_Detail> orderDetailList = orderDetailRepository.findAllByOrderId(order.getId());
        for (Order_Detail order_detail : orderDetailList) {
          for (Integer key : list.keySet()) {
            if (key.equals(order_detail.getProduct().getId())) {
              list.put(key, list.get(key) + order_detail.getNumber());
            }
          }
        }
      }
      Set<Map.Entry<Integer, Integer>> entries = list.entrySet();
      Comparator<Map.Entry<Integer, Integer>> comparator1 = new Comparator<Entry<Integer, Integer>>() {
        @Override
        public int compare(Map.Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {

          Integer v1 = o1.getValue();
          Integer v2 = o2.getValue();
          return v2.compareTo(v1);

        }
      };

      List<Entry<Integer, Integer>> listEntries = new ArrayList<>(entries);

      Collections.sort(listEntries, comparator1);

      LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>(listEntries.size());
      for (Entry<Integer, Integer> entry : listEntries) {
        sortedMap.put(entry.getKey(), entry.getValue());
      }
      Set<Entry<Integer, Integer>> sortedEntries = sortedMap.entrySet();

      for (Entry<Integer, Integer> mapping : sortedEntries) {
        Product product = productRepository.findById(mapping.getKey())
                .orElseThrow(() -> new NotFoundException("product-not-found"));
        insertCell(table, String.valueOf(product.getId()), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, product.getName(), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, product.getCapacity().toString(), Element.ALIGN_CENTER, 1,
                font14);
        insertCell(table, mapping.getValue().toString(), Element.ALIGN_CENTER, 1,
                font14);
      }

      // set table width a percentage of the page width
      table.setWidthPercentage(80);

      pDate.setAlignment(Paragraph.ALIGN_CENTER);
      pStaff.setAlignment(Paragraph.ALIGN_CENTER);
      pYear.setAlignment(Paragraph.ALIGN_CENTER);
      p.setAlignment(Paragraph.ALIGN_CENTER);

      //add the PDF table to the paragraph
      paragraph.add(table);
      // add the paragraph to the document
//      document.add(image);
      document.add(pTieuDe);
      document.add(p);
      document.add(pYear);
      document.add(pStaff);
      document.add(pTemp);
      document.add(pDate);
      document.add(pTemp);
      document.add(paragraph);
      document.add(pTemp);
      document.close();
      runPDF(pdfFilePath);
    } catch (IOException exception) {
      System.out.print(exception.getMessage());
    } catch (Exception t) {
      System.out.print(t.getMessage());
    }
  }

  private final String BILL_NOT_FOUND_ERROR = "Bill not found.";
  private static final Font font16 = com.itextpdf.text.FontFactory
          .getFont(FontFactory.TIMES_BOLD, 16);
  private static final Font font15 = com.itextpdf.text.FontFactory
          .getFont(FontFactory.TIMES_BOLD, 15);
  private static final Font font14 = com.itextpdf.text.FontFactory
          .getFont(FontFactory.TIMES_BOLD, 14);
  private static final Font font1 = com.itextpdf.text.FontFactory
          .getFont(FontFactory.HELVETICA_BOLD, 22);
  private static final Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD,
          new BaseColor(0, 0, 0));
  private static final Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);


}
