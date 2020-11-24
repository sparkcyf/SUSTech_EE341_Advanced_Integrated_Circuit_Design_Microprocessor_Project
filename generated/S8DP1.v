module tag_refine( // @[:@10.2]
  input clock, // @[:@11.4]
  input reset, // @[:@12.4]
  input io_tag_in_0, // @[:@13.4]
  input io_tag_in_1, // @[:@13.4]
  input io_tag_in_2, // @[:@13.4]
  input io_tag_in_3, // @[:@13.4]
  input io_tag_in_4, // @[:@13.4]
  input io_tag_in_5, // @[:@13.4]
  input io_tag_in_6, // @[:@13.4]
  input io_tag_in_7, // @[:@13.4]
  output io_tag_out_0, // @[:@13.4]
  output io_tag_out_1, // @[:@13.4]
  output io_tag_out_2, // @[:@13.4]
  output io_tag_out_3, // @[:@13.4]
  output io_tag_out_4, // @[:@13.4]
  output io_tag_out_5, // @[:@13.4]
  output io_tag_out_6, // @[:@13.4]
  output io_tag_out_7 // @[:@13.4]
);
  reg tag_0; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_0;
  reg tag_1; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_1;
  reg tag_2; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_2;
  reg tag_3; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_3;
  reg tag_4; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_4;
  reg tag_5; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_5;
  reg tag_6; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_6;
  reg tag_7; // @[tag_refine.scala 28:20:@24.4]
  reg [31:0] _RAND_7;
  reg find; // @[tag_refine.scala 29:21:@25.4]
  reg [31:0] _RAND_8;
  wire _T_130; // @[tag_refine.scala 34:15:@34.4]
  wire _GEN_0; // @[tag_refine.scala 35:31:@37.6]
  wire _GEN_1; // @[tag_refine.scala 35:31:@37.6]
  wire _GEN_2; // @[tag_refine.scala 34:28:@35.4]
  wire _GEN_3; // @[tag_refine.scala 34:28:@35.4]
  wire _GEN_4; // @[tag_refine.scala 35:31:@45.6]
  wire _GEN_5; // @[tag_refine.scala 35:31:@45.6]
  wire _GEN_6; // @[tag_refine.scala 34:28:@43.4]
  wire _GEN_7; // @[tag_refine.scala 34:28:@43.4]
  wire _GEN_8; // @[tag_refine.scala 35:31:@53.6]
  wire _GEN_9; // @[tag_refine.scala 35:31:@53.6]
  wire _GEN_10; // @[tag_refine.scala 34:28:@51.4]
  wire _GEN_11; // @[tag_refine.scala 34:28:@51.4]
  wire _GEN_12; // @[tag_refine.scala 35:31:@61.6]
  wire _GEN_13; // @[tag_refine.scala 35:31:@61.6]
  wire _GEN_14; // @[tag_refine.scala 34:28:@59.4]
  wire _GEN_15; // @[tag_refine.scala 34:28:@59.4]
  wire _GEN_16; // @[tag_refine.scala 35:31:@69.6]
  wire _GEN_17; // @[tag_refine.scala 35:31:@69.6]
  wire _GEN_18; // @[tag_refine.scala 34:28:@67.4]
  wire _GEN_19; // @[tag_refine.scala 34:28:@67.4]
  wire _GEN_20; // @[tag_refine.scala 35:31:@77.6]
  wire _GEN_21; // @[tag_refine.scala 35:31:@77.6]
  wire _GEN_22; // @[tag_refine.scala 34:28:@75.4]
  wire _GEN_23; // @[tag_refine.scala 34:28:@75.4]
  wire _GEN_24; // @[tag_refine.scala 35:31:@85.6]
  wire _GEN_25; // @[tag_refine.scala 35:31:@85.6]
  wire _GEN_26; // @[tag_refine.scala 34:28:@83.4]
  wire _GEN_27; // @[tag_refine.scala 34:28:@83.4]
  wire _GEN_28; // @[tag_refine.scala 35:31:@93.6]
  wire _GEN_29; // @[tag_refine.scala 35:31:@93.6]
  wire _GEN_30; // @[tag_refine.scala 34:28:@91.4]
  wire _GEN_31; // @[tag_refine.scala 34:28:@91.4]
  assign _T_130 = find == 1'h0; // @[tag_refine.scala 34:15:@34.4]
  assign _GEN_0 = tag_0 ? 1'h0:io_tag_in_0; // @[tag_refine.scala 35:31:@37.6]
  assign _GEN_1 = tag_0 ? 1'h1:find; // @[tag_refine.scala 35:31:@37.6]
  assign _GEN_2 = _T_130 ? _GEN_0:io_tag_in_0; // @[tag_refine.scala 34:28:@35.4]
  assign _GEN_3 = _T_130 ? _GEN_1:find; // @[tag_refine.scala 34:28:@35.4]
  assign _GEN_4 = tag_1 ? 1'h0:io_tag_in_1; // @[tag_refine.scala 35:31:@45.6]
  assign _GEN_5 = tag_1 ? 1'h1:_GEN_3; // @[tag_refine.scala 35:31:@45.6]
  assign _GEN_6 = _T_130 ? _GEN_4:io_tag_in_1; // @[tag_refine.scala 34:28:@43.4]
  assign _GEN_7 = _T_130 ? _GEN_5:_GEN_3; // @[tag_refine.scala 34:28:@43.4]
  assign _GEN_8 = tag_2 ? 1'h0:io_tag_in_2; // @[tag_refine.scala 35:31:@53.6]
  assign _GEN_9 = tag_2 ? 1'h1:_GEN_7; // @[tag_refine.scala 35:31:@53.6]
  assign _GEN_10 = _T_130 ? _GEN_8:io_tag_in_2; // @[tag_refine.scala 34:28:@51.4]
  assign _GEN_11 = _T_130 ? _GEN_9:_GEN_7; // @[tag_refine.scala 34:28:@51.4]
  assign _GEN_12 = tag_3 ? 1'h0:io_tag_in_3; // @[tag_refine.scala 35:31:@61.6]
  assign _GEN_13 = tag_3 ? 1'h1:_GEN_11; // @[tag_refine.scala 35:31:@61.6]
  assign _GEN_14 = _T_130 ? _GEN_12:io_tag_in_3; // @[tag_refine.scala 34:28:@59.4]
  assign _GEN_15 = _T_130 ? _GEN_13:_GEN_11; // @[tag_refine.scala 34:28:@59.4]
  assign _GEN_16 = tag_4 ? 1'h0:io_tag_in_4; // @[tag_refine.scala 35:31:@69.6]
  assign _GEN_17 = tag_4 ? 1'h1:_GEN_15; // @[tag_refine.scala 35:31:@69.6]
  assign _GEN_18 = _T_130 ? _GEN_16:io_tag_in_4; // @[tag_refine.scala 34:28:@67.4]
  assign _GEN_19 = _T_130 ? _GEN_17:_GEN_15; // @[tag_refine.scala 34:28:@67.4]
  assign _GEN_20 = tag_5 ? 1'h0:io_tag_in_5; // @[tag_refine.scala 35:31:@77.6]
  assign _GEN_21 = tag_5 ? 1'h1:_GEN_19; // @[tag_refine.scala 35:31:@77.6]
  assign _GEN_22 = _T_130 ? _GEN_20:io_tag_in_5; // @[tag_refine.scala 34:28:@75.4]
  assign _GEN_23 = _T_130 ? _GEN_21:_GEN_19; // @[tag_refine.scala 34:28:@75.4]
  assign _GEN_24 = tag_6 ? 1'h0:io_tag_in_6; // @[tag_refine.scala 35:31:@85.6]
  assign _GEN_25 = tag_6 ? 1'h1:_GEN_23; // @[tag_refine.scala 35:31:@85.6]
  assign _GEN_26 = _T_130 ? _GEN_24:io_tag_in_6; // @[tag_refine.scala 34:28:@83.4]
  assign _GEN_27 = _T_130 ? _GEN_25:_GEN_23; // @[tag_refine.scala 34:28:@83.4]
  assign _GEN_28 = tag_7 ? 1'h0:io_tag_in_7; // @[tag_refine.scala 35:31:@93.6]
  assign _GEN_29 = tag_7 ? 1'h1:_GEN_27; // @[tag_refine.scala 35:31:@93.6]
  assign _GEN_30 = _T_130 ? _GEN_28:io_tag_in_7; // @[tag_refine.scala 34:28:@91.4]
  assign _GEN_31 = _T_130 ? _GEN_29:_GEN_27; // @[tag_refine.scala 34:28:@91.4]
  assign io_tag_out_0 = tag_0; // @[tag_refine.scala 42:14:@98.4]
  assign io_tag_out_1 = tag_1; // @[tag_refine.scala 42:14:@99.4]
  assign io_tag_out_2 = tag_2; // @[tag_refine.scala 42:14:@100.4]
  assign io_tag_out_3 = tag_3; // @[tag_refine.scala 42:14:@101.4]
  assign io_tag_out_4 = tag_4; // @[tag_refine.scala 42:14:@102.4]
  assign io_tag_out_5 = tag_5; // @[tag_refine.scala 42:14:@103.4]
  assign io_tag_out_6 = tag_6; // @[tag_refine.scala 42:14:@104.4]
  assign io_tag_out_7 = tag_7; // @[tag_refine.scala 42:14:@105.4]
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE
  integer initvar;
  initial begin
`ifndef verilator
  #0.002 begin end
`endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{$random}};
  tag_0 = _RAND_0[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{$random}};
  tag_1 = _RAND_1[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_2 = {1{$random}};
  tag_2 = _RAND_2[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_3 = {1{$random}};
  tag_3 = _RAND_3[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_4 = {1{$random}};
  tag_4 = _RAND_4[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_5 = {1{$random}};
  tag_5 = _RAND_5[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_6 = {1{$random}};
  tag_6 = _RAND_6[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_7 = {1{$random}};
  tag_7 = _RAND_7[0:0];
`endif // RANDOMIZE_REG_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_8 = {1{$random}};
  find = _RAND_8[0:0];
`endif // RANDOMIZE_REG_INIT
  end
`endif // RANDOMIZE
  always @(posedge clock) begin
    if (reset) begin
      tag_0 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_0) begin
          tag_0 <= 1'h0;
        end else begin
          tag_0 <= io_tag_in_0;
        end
      end else begin
        tag_0 <= io_tag_in_0;
      end
    end
    if (reset) begin
      tag_1 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_1) begin
          tag_1 <= 1'h0;
        end else begin
          tag_1 <= io_tag_in_1;
        end
      end else begin
        tag_1 <= io_tag_in_1;
      end
    end
    if (reset) begin
      tag_2 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_2) begin
          tag_2 <= 1'h0;
        end else begin
          tag_2 <= io_tag_in_2;
        end
      end else begin
        tag_2 <= io_tag_in_2;
      end
    end
    if (reset) begin
      tag_3 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_3) begin
          tag_3 <= 1'h0;
        end else begin
          tag_3 <= io_tag_in_3;
        end
      end else begin
        tag_3 <= io_tag_in_3;
      end
    end
    if (reset) begin
      tag_4 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_4) begin
          tag_4 <= 1'h0;
        end else begin
          tag_4 <= io_tag_in_4;
        end
      end else begin
        tag_4 <= io_tag_in_4;
      end
    end
    if (reset) begin
      tag_5 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_5) begin
          tag_5 <= 1'h0;
        end else begin
          tag_5 <= io_tag_in_5;
        end
      end else begin
        tag_5 <= io_tag_in_5;
      end
    end
    if (reset) begin
      tag_6 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_6) begin
          tag_6 <= 1'h0;
        end else begin
          tag_6 <= io_tag_in_6;
        end
      end else begin
        tag_6 <= io_tag_in_6;
      end
    end
    if (reset) begin
      tag_7 <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_7) begin
          tag_7 <= 1'h0;
        end else begin
          tag_7 <= io_tag_in_7;
        end
      end else begin
        tag_7 <= io_tag_in_7;
      end
    end
    if (reset) begin
      find <= 1'h0;
    end else begin
      if (_T_130) begin
        if (tag_7) begin
          find <= 1'h1;
        end else begin
          if (_T_130) begin
            if (tag_6) begin
              find <= 1'h1;
            end else begin
              if (_T_130) begin
                if (tag_5) begin
                  find <= 1'h1;
                end else begin
                  if (_T_130) begin
                    if (tag_4) begin
                      find <= 1'h1;
                    end else begin
                      if (_T_130) begin
                        if (tag_3) begin
                          find <= 1'h1;
                        end else begin
                          if (_T_130) begin
                            if (tag_2) begin
                              find <= 1'h1;
                            end else begin
                              if (_T_130) begin
                                if (tag_1) begin
                                  find <= 1'h1;
                                end else begin
                                  if (_T_130) begin
                                    if (tag_0) begin
                                      find <= 1'h1;
                                    end
                                  end
                                end
                              end else begin
                                if (_T_130) begin
                                  if (tag_0) begin
                                    find <= 1'h1;
                                  end
                                end
                              end
                            end
                          end else begin
                            if (_T_130) begin
                              if (tag_1) begin
                                find <= 1'h1;
                              end else begin
                                if (_T_130) begin
                                  if (tag_0) begin
                                    find <= 1'h1;
                                  end
                                end
                              end
                            end else begin
                              if (_T_130) begin
                                if (tag_0) begin
                                  find <= 1'h1;
                                end
                              end
                            end
                          end
                        end
                      end else begin
                        if (_T_130) begin
                          if (tag_2) begin
                            find <= 1'h1;
                          end else begin
                            if (_T_130) begin
                              if (tag_1) begin
                                find <= 1'h1;
                              end else begin
                                find <= _GEN_3;
                              end
                            end else begin
                              find <= _GEN_3;
                            end
                          end
                        end else begin
                          if (_T_130) begin
                            if (tag_1) begin
                              find <= 1'h1;
                            end else begin
                              find <= _GEN_3;
                            end
                          end else begin
                            find <= _GEN_3;
                          end
                        end
                      end
                    end
                  end else begin
                    if (_T_130) begin
                      if (tag_3) begin
                        find <= 1'h1;
                      end else begin
                        if (_T_130) begin
                          if (tag_2) begin
                            find <= 1'h1;
                          end else begin
                            find <= _GEN_7;
                          end
                        end else begin
                          find <= _GEN_7;
                        end
                      end
                    end else begin
                      if (_T_130) begin
                        if (tag_2) begin
                          find <= 1'h1;
                        end else begin
                          find <= _GEN_7;
                        end
                      end else begin
                        find <= _GEN_7;
                      end
                    end
                  end
                end
              end else begin
                if (_T_130) begin
                  if (tag_4) begin
                    find <= 1'h1;
                  end else begin
                    if (_T_130) begin
                      if (tag_3) begin
                        find <= 1'h1;
                      end else begin
                        find <= _GEN_11;
                      end
                    end else begin
                      find <= _GEN_11;
                    end
                  end
                end else begin
                  if (_T_130) begin
                    if (tag_3) begin
                      find <= 1'h1;
                    end else begin
                      find <= _GEN_11;
                    end
                  end else begin
                    find <= _GEN_11;
                  end
                end
              end
            end
          end else begin
            if (_T_130) begin
              if (tag_5) begin
                find <= 1'h1;
              end else begin
                if (_T_130) begin
                  if (tag_4) begin
                    find <= 1'h1;
                  end else begin
                    find <= _GEN_15;
                  end
                end else begin
                  find <= _GEN_15;
                end
              end
            end else begin
              if (_T_130) begin
                if (tag_4) begin
                  find <= 1'h1;
                end else begin
                  find <= _GEN_15;
                end
              end else begin
                find <= _GEN_15;
              end
            end
          end
        end
      end else begin
        if (_T_130) begin
          if (tag_6) begin
            find <= 1'h1;
          end else begin
            if (_T_130) begin
              if (tag_5) begin
                find <= 1'h1;
              end else begin
                find <= _GEN_19;
              end
            end else begin
              find <= _GEN_19;
            end
          end
        end else begin
          if (_T_130) begin
            if (tag_5) begin
              find <= 1'h1;
            end else begin
              find <= _GEN_19;
            end
          end else begin
            find <= _GEN_19;
          end
        end
      end
    end
  end
endmodule
module S8DP1( // @[:@107.2]
  input clock, // @[:@108.4]
  input reset, // @[:@109.4]
  input [31:0] io_int_in_A_0, // @[:@110.4]
  input [31:0] io_int_in_A_1, // @[:@110.4]
  input [31:0] io_int_in_A_2, // @[:@110.4]
  input [31:0] io_int_in_A_3, // @[:@110.4]
  input [31:0] io_int_in_A_4, // @[:@110.4]
  input [31:0] io_int_in_A_5, // @[:@110.4]
  input [31:0] io_int_in_A_6, // @[:@110.4]
  input [31:0] io_int_in_A_7, // @[:@110.4]
  input [31:0] io_int_in_B, // @[:@110.4]
  input io_tag_0, // @[:@110.4]
  input io_tag_1, // @[:@110.4]
  input io_tag_2, // @[:@110.4]
  input io_tag_3, // @[:@110.4]
  input io_tag_4, // @[:@110.4]
  input io_tag_5, // @[:@110.4]
  input io_tag_6, // @[:@110.4]
  input io_tag_7, // @[:@110.4]
  output io_out_tag_0, // @[:@110.4]
  output io_out_tag_1, // @[:@110.4]
  output io_out_tag_2, // @[:@110.4]
  output io_out_tag_3, // @[:@110.4]
  output io_out_tag_4, // @[:@110.4]
  output io_out_tag_5, // @[:@110.4]
  output io_out_tag_6, // @[:@110.4]
  output io_out_tag_7, // @[:@110.4]
  output [31:0] io_result // @[:@110.4]
);
  wire tag_change_clock; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_reset; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_0; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_1; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_2; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_3; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_4; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_5; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_6; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_in_7; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_0; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_1; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_2; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_3; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_4; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_5; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_6; // @[S8DP1.scala 30:26:@116.4]
  wire tag_change_io_tag_out_7; // @[S8DP1.scala 30:26:@116.4]
  reg [31:0] acc; // @[S8DP1.scala 28:20:@112.4]
  reg [31:0] _RAND_0;
  wire [63:0] _T_89; // @[S8DP1.scala 35:28:@135.4]
  wire [63:0] _GEN_0; // @[S8DP1.scala 35:14:@136.4]
  wire [64:0] _T_90; // @[S8DP1.scala 35:14:@136.4]
  wire [63:0] _T_91; // @[S8DP1.scala 35:14:@137.4]
  wire [63:0] _T_92; // @[S8DP1.scala 35:14:@138.4]
  wire [31:0] _GEN_1; // @[S8DP1.scala 35:7:@139.4]
  tag_refine tag_change( // @[S8DP1.scala 30:26:@116.4]
    .clock(tag_change_clock),
    .reset(tag_change_reset),
    .io_tag_in_0(tag_change_io_tag_in_0),
    .io_tag_in_1(tag_change_io_tag_in_1),
    .io_tag_in_2(tag_change_io_tag_in_2),
    .io_tag_in_3(tag_change_io_tag_in_3),
    .io_tag_in_4(tag_change_io_tag_in_4),
    .io_tag_in_5(tag_change_io_tag_in_5),
    .io_tag_in_6(tag_change_io_tag_in_6),
    .io_tag_in_7(tag_change_io_tag_in_7),
    .io_tag_out_0(tag_change_io_tag_out_0),
    .io_tag_out_1(tag_change_io_tag_out_1),
    .io_tag_out_2(tag_change_io_tag_out_2),
    .io_tag_out_3(tag_change_io_tag_out_3),
    .io_tag_out_4(tag_change_io_tag_out_4),
    .io_tag_out_5(tag_change_io_tag_out_5),
    .io_tag_out_6(tag_change_io_tag_out_6),
    .io_tag_out_7(tag_change_io_tag_out_7)
  );
  assign _T_89 = $signed(io_int_in_B)*$signed(32'sh0); // @[S8DP1.scala 35:28:@135.4]
  assign _GEN_0 = {{32{acc[31]}}, acc}; // @[S8DP1.scala 35:14:@136.4]
  assign _T_90 = $signed(_GEN_0)+$signed(_T_89); // @[S8DP1.scala 35:14:@136.4]
  assign _T_91 = _T_90[63:0]; // @[S8DP1.scala 35:14:@137.4]
  assign _T_92 = $signed(_T_91); // @[S8DP1.scala 35:14:@138.4]
  assign io_out_tag_0 = tag_change_io_tag_out_0; // @[S8DP1.scala 38:14:@148.4]
  assign io_out_tag_1 = tag_change_io_tag_out_1; // @[S8DP1.scala 38:14:@149.4]
  assign io_out_tag_2 = tag_change_io_tag_out_2; // @[S8DP1.scala 38:14:@150.4]
  assign io_out_tag_3 = tag_change_io_tag_out_3; // @[S8DP1.scala 38:14:@151.4]
  assign io_out_tag_4 = tag_change_io_tag_out_4; // @[S8DP1.scala 38:14:@152.4]
  assign io_out_tag_5 = tag_change_io_tag_out_5; // @[S8DP1.scala 38:14:@153.4]
  assign io_out_tag_6 = tag_change_io_tag_out_6; // @[S8DP1.scala 38:14:@154.4]
  assign io_out_tag_7 = tag_change_io_tag_out_7; // @[S8DP1.scala 38:14:@155.4]
  assign io_result = acc; // @[S8DP1.scala 40:13:@156.4]
  assign _GEN_1 = _T_92[31:0]; // @[S8DP1.scala 35:7:@139.4]
  assign tag_change_clock = clock; // @[:@117.4]
  assign tag_change_reset = reset; // @[:@118.4]
  assign tag_change_io_tag_in_0 = io_tag_0; // @[S8DP1.scala 37:24:@140.4]
  assign tag_change_io_tag_in_1 = io_tag_1; // @[S8DP1.scala 37:24:@141.4]
  assign tag_change_io_tag_in_2 = io_tag_2; // @[S8DP1.scala 37:24:@142.4]
  assign tag_change_io_tag_in_3 = io_tag_3; // @[S8DP1.scala 37:24:@143.4]
  assign tag_change_io_tag_in_4 = io_tag_4; // @[S8DP1.scala 37:24:@144.4]
  assign tag_change_io_tag_in_5 = io_tag_5; // @[S8DP1.scala 37:24:@145.4]
  assign tag_change_io_tag_in_6 = io_tag_6; // @[S8DP1.scala 37:24:@146.4]
  assign tag_change_io_tag_in_7 = io_tag_7; // @[S8DP1.scala 37:24:@147.4]
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE
  integer initvar;
  initial begin
`ifndef verilator
  #0.002 begin end
`endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{$random}};
  acc = _RAND_0[31:0];
`endif // RANDOMIZE_REG_INIT
  end
`endif // RANDOMIZE
  always @(posedge clock) begin
    if (reset) begin
      acc <= 32'sh0;
    end else begin
      acc <= $signed(_GEN_1);
    end
  end
endmodule
