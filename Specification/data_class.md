
# Classes

1. Dynasty
2. HistoricalCharacter
3. HistoricalSite
4. CulturalFestival
5. HistoricEvent

## Description

1. Dynasty
	- About (Wiki): Là danh từ để gọi chung hai hay nhiều vua cùng một gia đình nối tiếp nhau trị vì một lãnh thổ nào đó.
	- Relationships:
		1. List of HistoricalCharacter
	- Properties
		1. CountryName - Tên quốc gia
		2. Duration - Thời gian
		3. Capital - Thủ đô
		4. Language - Ngôn ngữ thông dụng
		5. Education - Tôn giáo chính
		6. Politics - Chính phủ
		7. KingList - Danh sách vua (listHistoricalCharacter)
		8. History - Lịch sử (listHistoricEvent)
		9. Population - Dân số
		10. Currency - Đơn vị tiền tệ
		11. Predecessor - Tiền thân (Dynasty)
		12. Successor - Kế tục (Dynasty)

	
2. HistoricalCharacter
	- About (Tự hiểu): là những nhân vật được có tiểu sử được ghi chép và được biết đến
	- Relationships
		1. Under (a) Dynasty
		2. Related to Historical Site
		3. Related to CulturalFestival
		4. Related to HistoricEvent
	- Properties
		1. Name
		2. DateOfBirth
		3. DateOfDeath
		4. Biography
		5. Career
	
3. HistoricalSite
	- About (Wiki): là dấu vết của quá khứ được lưu lại trên hoặc trong mặt đất có ý nghĩa về văn hóa và lịch sử
	- Relationships
		1. Related to Dynasty
		2. Related to HistoricalCharacter
		3. Related to CulturalFestival
		4. Related to HistoricEvent
		
4. CulturalFestival
	- About (Wiki): là sự kiện văn hóa được tổ chức mang tính cộng đồng
	- Relationships
		1. Related to Dynasty
		2. Related to HistoricalCharacter
		3. Related to HistoricalSite
		4. Related to HistoricEvent

5. HistoricEvent
	- About (Wiki-self): là những sự việc diễn ra trong quá khứ, được ghi chép lại
	- Relationships
		1. Under a Dynasty
		2. Related to HistoricalCharacter
		3. Related to HistoricalSite
		4. Related to Historical Festival