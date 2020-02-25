package datapath
import chisel3._

import chisel3.util.Cat
import chisel3.util.Fill

class alu extends Module{
	val io=IO(new Bundle{
		val A=Input(SInt(32.W))
		val B=Input(SInt(32.W))
		val x=Output(SInt(32.W))
		val AluBranch = Output(UInt(1.W))
		val Aluop=Input(UInt(5.W))
		
			
	})
		io.x := 0.S
		
		when(io.Aluop === "b00000".U){
			io.x:=io.A+io.B
		}.elsewhen(io.Aluop ==="b00001".U){
			val sbt = io.A.asUInt
			val sbt1 = io.B.asUInt
			val sbt3 = io.B(4,0)
			val sbt4 = sbt << sbt3
			val sbt5 = sbt4.asSInt
			io.x := sbt5
		}.elsewhen(io.Aluop ==="b11111".U){
			io.x:=io.A
		}.elsewhen(io.Aluop ==="b00001".U){
			when(io.A < io.B){
			io.x := 1.S
			}.otherwise{
			io.x := 0.S
			}
		}.elsewhen((io.Aluop === "b00011".U) | (io.Aluop === "b10110".U)){			
			val a1 = io.A.asUInt
			val b1 = io.B.asUInt
			when(a1 < b1){
				io.x := 1.S
			}.otherwise{
				io.x := 0.S
			}
		}.elsewhen(io.Aluop === "b00100".U){							
		io.x := io.A ^ io.B
		}.elsewhen(io.Aluop === "b00101".U){							
		val A2 = io.A(4,0)
		val B2 = io.B(4,0)		
		val shift = io.A.asUInt >> io.B.asUInt
		io.x := shift.asSInt
		}.elsewhen(io.Aluop === "b00110".U){							
			io.x := io.A | io.B
		}.elsewhen(io.Aluop === "b00111".U){							
			io.x := io.A & io.B
		}.elsewhen(io.Aluop === "b01000".U){							
			io.x := io.A - io.B
		}.elsewhen(io.Aluop === "b01101".U){							
			io.x := (io.A(4,0) >> io.B(4,0)).asSInt
		}.elsewhen(io.Aluop === "b10000".U){							
			when(io.A === io.B){
				io.x := 1.S
			}.otherwise{
				io.x := 0.S
			}
		}.elsewhen(io.Aluop === "b10001".U){							
			when(io.A === io.B){
			io.x := 0.S
			}.otherwise{
				io.x := 1.S
			}
		}.elsewhen(io.Aluop === "b10100".U){							
			when(io.A < io.B){
				io.x := 1.S
			}.otherwise{
				io.x := 0.S
			}
		}.elsewhen(io.Aluop === "b10101".U){							
			when((io.A === io.B) | (io.A > io.B)){
				io.x := 1.S
			}.otherwise{
				io.x := 0.S
			}
		}.elsewhen(io.Aluop === "b10111".U){							
			val a3 = io.A.asUInt
			val b3 = io.B.asUInt
			when((a3 === b3) | (a3 > b3)){
				io.x := 1.S
			}.otherwise{
				io.x := 0.S
			}

		}



		when((io.Aluop(4,3) === "b10".U) & (io.x === 1.S)){
		io.AluBranch := 1.U
		}.otherwise{
		io.AluBranch := 0.U
		}	
}

		
		

