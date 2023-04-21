package kr.co.kpcard.ocrscraping.common.excel.service

import kr.co.kpcard.ocrscraping.common.config.EnvProperties
import kr.co.kpcard.ocrscraping.common.excel.model.ProductInfoDto
import kr.co.kpcard.ocrscraping.common.enums.IssuerType
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.FileOutputStream

@Service
class ExcelService(
    private val envProperties: EnvProperties
) {
    fun create(dataList: List<ProductInfoDto>) {
        var rowNumber = 1
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("OCR 상품 정보")
        val fileName = IssuerType.findByCode(dataList.first().issuer)?.name ?: ""
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("순번")
        headerRow.createCell(1).setCellValue("발급사")
        headerRow.createCell(2).setCellValue("상품명")
        headerRow.createCell(3).setCellValue("브랜드")
        headerRow.createCell(4).setCellValue("연관상품명")
        headerRow.createCell(5).setCellValue("연관브랜드")
        headerRow.createCell(6).setCellValue("카테고리코드")
        headerRow.createCell(7).setCellValue("카테고리명")
        headerRow.createCell(8).setCellValue("쿠폰타입코드")
        headerRow.createCell(9).setCellValue("쿠폰타입명")
        headerRow.createCell(10).setCellValue("가격")
        headerRow.createCell(11).setCellValue("설명")
        headerRow.createCell(12).setCellValue("이미지 파일명")

        dataList.forEach {
            val row = sheet.createRow(rowNumber++)
            var cellIdx = 0
            row.createCell(cellIdx++).setCellValue(it.seqNo.toString())
            row.createCell(cellIdx++).setCellValue(it.issuer)
            row.createCell(cellIdx++).setCellValue(it.title)
            row.createCell(cellIdx++).setCellValue(it.brand)
            row.createCell(cellIdx++).setCellValue(it.searchTitle)
            row.createCell(cellIdx++).setCellValue(it.searchBrand)
            row.createCell(cellIdx++).setCellValue(it.categoryCode)
            row.createCell(cellIdx++).setCellValue(it.categoryDesc)
            row.createCell(cellIdx++).setCellValue(it.couponTypeCode)
            row.createCell(cellIdx++).setCellValue(it.couponTypeDesc)
            row.createCell(cellIdx++).setCellValue(it.price)
            row.createCell(cellIdx++).setCellValue(it.content)
            row.createCell(cellIdx).setCellValue(it.imageUrl)
        }

        val fos = FileOutputStream("${envProperties.savePath.excel}${fileName}.xlsx")
        workbook.write(fos)
        workbook.close()
    }
}