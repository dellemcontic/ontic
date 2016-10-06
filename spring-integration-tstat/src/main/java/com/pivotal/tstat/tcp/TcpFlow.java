package com.pivotal.tstat.tcp;

import java.io.Serializable;

public class TcpFlow implements Serializable{

	private static final long serialVersionUID = -5055683890267125088L;

	String m_c_ip;

	String m_c_port;

	String m_c_pkts_all;

	String m_c_rst_cnt;

	String m_c_ack_cnt;

	String m_c_ack_cnt_p;

	String m_c_bytes_uniq;

	String m_c_pkts_data;

	String m_c_bytes_all;

	String m_c_pkts_retx;

	String m_c_bytes_retx;

	String m_c_pkts_ooo;

	String m_c_syn_cnt;

	String m_c_fin_cnt;

	String m_c_f1323_opt;

	String m_c_tm_opt;

	String m_c_win_scl;

	String m_c_sack_opt;

	String m_c_sack_cnt;

	String m_c_mss;

	String m_c_mss_max;

	String m_c_mss_min;

	String m_c_win_max;

	String m_c_win_min;

	String m_c_win_0;

	String m_c_cwin_max;

	String m_c_cwin_min;

	String m_c_cwin_ini;

	String m_c_rtt_avg;

	String m_c_rtt_min;

	String m_c_rtt_max;

	String m_c_rtt_std;

	String m_c_rtt_cnt;

	String m_c_ttl_min;

	String m_c_ttl_max;

	String m_c_pkts_rto;

	String m_c_pkts_fs;

	String m_c_pkts_reor;

	String m_c_pkts_dup;

	String m_c_pkts_unk;

	String m_c_pkts_fc;

	String m_c_pkts_unrto;

	String m_c_pkts_unfs;

	String m_c_syn_retx;

	String m_s_ip;

	String m_s_port;

	String m_s_pkts_all;

	String m_s_rst_cnt;

	String m_s_ack_cnt;

	String m_s_ack_cnt_p;

	String m_s_bytes_uniq;

	String m_s_pkts_data;

	String m_s_bytes_all;

	String m_s_pkts_retx;

	String m_s_bytes_retx;

	String m_s_pkts_ooo;

	String m_s_syn_cnt;

	String m_s_fin_cnt;

	String m_s_f1323_opt;

	String m_s_tm_opt;

	String m_s_win_scl;

	String m_s_sack_opt;

	String m_s_sack_cnt;

	String m_s_mss;

	String m_s_mss_max;

	String m_s_mss_min;

	String m_s_win_max;

	String m_s_win_min;

	String m_s_win_0;

	String m_s_cwin_max;

	String m_s_cwin_min;

	String m_s_cwin_ini;

	String m_s_rtt_avg;

	String m_s_rtt_min;

	String m_s_rtt_max;

	String m_s_rtt_std;

	String m_s_rtt_cnt;

	String m_s_ttl_min;

	String m_s_ttl_max;

	String m_s_pkts_rto;

	String m_s_pkts_fs;

	String m_s_pkts_reor;

	String m_s_pkts_dup;

	String m_s_pkts_unk;

	String m_s_pkts_fc;

	String m_s_pkts_unrto;

	String m_s_pkts_unfs;

	String m_s_syn_retx;

	String m_durat;

	String m_first;

	String m_last;

	String m_c_first;

	String m_s_first;

	String m_c_last;

	String m_s_last;

	String m_c_first_ack;

	String m_s_first_ack;

	String m_first_abs;

	String m_c_isint;

	String m_s_isint;

	String m_con_t;

	String m_p2p_t;

	String m_p2p_st;

	String m_ed2k_data;

	String m_ed2k_sig;

	String m_ed2k_c2s;

	String m_ed2k_c2c;

	String m_ed2k_chat;

	String m_http_t;

	String m_c_pkts_push;

	String m_s_pkts_push;

	String m_c_ssl;

	String m_s_ssl;

	String getc_ip(){
		return m_c_ip;
	}

	void setc_ip(String value){
		m_c_ip = value;
	}

	String getc_port(){
		return m_c_port;
	}

	void setc_port(String value){
		m_c_port = value;
	}

	String getc_pkts_all(){
		return m_c_pkts_all;
	}

	void setc_pkts_all(String value){
		m_c_pkts_all = value;
	}

	String getc_rst_cnt(){
		return m_c_rst_cnt;
	}

	void setc_rst_cnt(String value){
		m_c_rst_cnt = value;
	}

	String getc_ack_cnt(){
		return m_c_ack_cnt;
	}

	void setc_ack_cnt(String value){
		m_c_ack_cnt = value;
	}

	String getc_ack_cnt_p(){
		return m_c_ack_cnt_p;
	}

	void setc_ack_cnt_p(String value){
		m_c_ack_cnt_p = value;
	}

	String getc_bytes_uniq(){
		return m_c_bytes_uniq;
	}

	void setc_bytes_uniq(String value){
		m_c_bytes_uniq = value;
	}

	String getc_pkts_data(){
		return m_c_pkts_data;
	}

	void setc_pkts_data(String value){
		m_c_pkts_data = value;
	}

	String getc_bytes_all(){
		return m_c_bytes_all;
	}

	void setc_bytes_all(String value){
		m_c_bytes_all = value;
	}

	String getc_pkts_retx(){
		return m_c_pkts_retx;
	}

	void setc_pkts_retx(String value){
		m_c_pkts_retx = value;
	}

	String getc_bytes_retx(){
		return m_c_bytes_retx;
	}

	void setc_bytes_retx(String value){
		m_c_bytes_retx = value;
	}

	String getc_pkts_ooo(){
		return m_c_pkts_ooo;
	}

	void setc_pkts_ooo(String value){
		m_c_pkts_ooo = value;
	}

	String getc_syn_cnt(){
		return m_c_syn_cnt;
	}

	void setc_syn_cnt(String value){
		m_c_syn_cnt = value;
	}

	String getc_fin_cnt(){
		return m_c_fin_cnt;
	}

	void setc_fin_cnt(String value){
		m_c_fin_cnt = value;
	}

	String getc_f1323_opt(){
		return m_c_f1323_opt;
	}

	void setc_f1323_opt(String value){
		m_c_f1323_opt = value;
	}

	String getc_tm_opt(){
		return m_c_tm_opt;
	}

	void setc_tm_opt(String value){
		m_c_tm_opt = value;
	}

	String getc_win_scl(){
		return m_c_win_scl;
	}

	void setc_win_scl(String value){
		m_c_win_scl = value;
	}

	String getc_sack_opt(){
		return m_c_sack_opt;
	}

	void setc_sack_opt(String value){
		m_c_sack_opt = value;
	}

	String getc_sack_cnt(){
		return m_c_sack_cnt;
	}

	void setc_sack_cnt(String value){
		m_c_sack_cnt = value;
	}

	String getc_mss(){
		return m_c_mss;
	}

	void setc_mss(String value){
		m_c_mss = value;
	}

	String getc_mss_max(){
		return m_c_mss_max;
	}

	void setc_mss_max(String value){
		m_c_mss_max = value;
	}

	String getc_mss_min(){
		return m_c_mss_min;
	}

	void setc_mss_min(String value){
		m_c_mss_min = value;
	}

	String getc_win_max(){
		return m_c_win_max;
	}

	void setc_win_max(String value){
		m_c_win_max = value;
	}

	String getc_win_min(){
		return m_c_win_min;
	}

	void setc_win_min(String value){
		m_c_win_min = value;
	}

	String getc_win_0(){
		return m_c_win_0;
	}

	void setc_win_0(String value){
		m_c_win_0 = value;
	}

	String getc_cwin_max(){
		return m_c_cwin_max;
	}

	void setc_cwin_max(String value){
		m_c_cwin_max = value;
	}

	String getc_cwin_min(){
		return m_c_cwin_min;
	}

	void setc_cwin_min(String value){
		m_c_cwin_min = value;
	}

	String getc_cwin_ini(){
		return m_c_cwin_ini;
	}

	void setc_cwin_ini(String value){
		m_c_cwin_ini = value;
	}

	String getc_rtt_avg(){
		return m_c_rtt_avg;
	}

	void setc_rtt_avg(String value){
		m_c_rtt_avg = value;
	}

	String getc_rtt_min(){
		return m_c_rtt_min;
	}

	void setc_rtt_min(String value){
		m_c_rtt_min = value;
	}

	String getc_rtt_max(){
		return m_c_rtt_max;
	}

	void setc_rtt_max(String value){
		m_c_rtt_max = value;
	}

	String getc_rtt_std(){
		return m_c_rtt_std;
	}

	void setc_rtt_std(String value){
		m_c_rtt_std = value;
	}

	String getc_rtt_cnt(){
		return m_c_rtt_cnt;
	}

	void setc_rtt_cnt(String value){
		m_c_rtt_cnt = value;
	}

	String getc_ttl_min(){
		return m_c_ttl_min;
	}

	void setc_ttl_min(String value){
		m_c_ttl_min = value;
	}

	String getc_ttl_max(){
		return m_c_ttl_max;
	}

	void setc_ttl_max(String value){
		m_c_ttl_max = value;
	}

	String getc_pkts_rto(){
		return m_c_pkts_rto;
	}

	void setc_pkts_rto(String value){
		m_c_pkts_rto = value;
	}

	String getc_pkts_fs(){
		return m_c_pkts_fs;
	}

	void setc_pkts_fs(String value){
		m_c_pkts_fs = value;
	}

	String getc_pkts_reor(){
		return m_c_pkts_reor;
	}

	void setc_pkts_reor(String value){
		m_c_pkts_reor = value;
	}

	String getc_pkts_dup(){
		return m_c_pkts_dup;
	}

	void setc_pkts_dup(String value){
		m_c_pkts_dup = value;
	}

	String getc_pkts_unk(){
		return m_c_pkts_unk;
	}

	void setc_pkts_unk(String value){
		m_c_pkts_unk = value;
	}

	String getc_pkts_fc(){
		return m_c_pkts_fc;
	}

	void setc_pkts_fc(String value){
		m_c_pkts_fc = value;
	}

	String getc_pkts_unrto(){
		return m_c_pkts_unrto;
	}

	void setc_pkts_unrto(String value){
		m_c_pkts_unrto = value;
	}

	String getc_pkts_unfs(){
		return m_c_pkts_unfs;
	}

	void setc_pkts_unfs(String value){
		m_c_pkts_unfs = value;
	}

	String getc_syn_retx(){
		return m_c_syn_retx;
	}

	void setc_syn_retx(String value){
		m_c_syn_retx = value;
	}

	String gets_ip(){
		return m_s_ip;
	}

	void sets_ip(String value){
		m_s_ip = value;
	}

	String gets_port(){
		return m_s_port;
	}

	void sets_port(String value){
		m_s_port = value;
	}

	String gets_pkts_all(){
		return m_s_pkts_all;
	}

	void sets_pkts_all(String value){
		m_s_pkts_all = value;
	}

	String gets_rst_cnt(){
		return m_s_rst_cnt;
	}

	void sets_rst_cnt(String value){
		m_s_rst_cnt = value;
	}

	String gets_ack_cnt(){
		return m_s_ack_cnt;
	}

	void sets_ack_cnt(String value){
		m_s_ack_cnt = value;
	}

	String gets_ack_cnt_p(){
		return m_s_ack_cnt_p;
	}

	void sets_ack_cnt_p(String value){
		m_s_ack_cnt_p = value;
	}

	String gets_bytes_uniq(){
		return m_s_bytes_uniq;
	}

	void sets_bytes_uniq(String value){
		m_s_bytes_uniq = value;
	}

	String gets_pkts_data(){
		return m_s_pkts_data;
	}

	void sets_pkts_data(String value){
		m_s_pkts_data = value;
	}

	String gets_bytes_all(){
		return m_s_bytes_all;
	}

	void sets_bytes_all(String value){
		m_s_bytes_all = value;
	}

	String gets_pkts_retx(){
		return m_s_pkts_retx;
	}

	void sets_pkts_retx(String value){
		m_s_pkts_retx = value;
	}

	String gets_bytes_retx(){
		return m_s_bytes_retx;
	}

	void sets_bytes_retx(String value){
		m_s_bytes_retx = value;
	}

	String gets_pkts_ooo(){
		return m_s_pkts_ooo;
	}

	void sets_pkts_ooo(String value){
		m_s_pkts_ooo = value;
	}

	String gets_syn_cnt(){
		return m_s_syn_cnt;
	}

	void sets_syn_cnt(String value){
		m_s_syn_cnt = value;
	}

	String gets_fin_cnt(){
		return m_s_fin_cnt;
	}

	void sets_fin_cnt(String value){
		m_s_fin_cnt = value;
	}

	String gets_f1323_opt(){
		return m_s_f1323_opt;
	}

	void sets_f1323_opt(String value){
		m_s_f1323_opt = value;
	}

	String gets_tm_opt(){
		return m_s_tm_opt;
	}

	void sets_tm_opt(String value){
		m_s_tm_opt = value;
	}

	String gets_win_scl(){
		return m_s_win_scl;
	}

	void sets_win_scl(String value){
		m_s_win_scl = value;
	}

	String gets_sack_opt(){
		return m_s_sack_opt;
	}

	void sets_sack_opt(String value){
		m_s_sack_opt = value;
	}

	String gets_sack_cnt(){
		return m_s_sack_cnt;
	}

	void sets_sack_cnt(String value){
		m_s_sack_cnt = value;
	}

	String gets_mss(){
		return m_s_mss;
	}

	void sets_mss(String value){
		m_s_mss = value;
	}

	String gets_mss_max(){
		return m_s_mss_max;
	}

	void sets_mss_max(String value){
		m_s_mss_max = value;
	}

	String gets_mss_min(){
		return m_s_mss_min;
	}

	void sets_mss_min(String value){
		m_s_mss_min = value;
	}

	String gets_win_max(){
		return m_s_win_max;
	}

	void sets_win_max(String value){
		m_s_win_max = value;
	}

	String gets_win_min(){
		return m_s_win_min;
	}

	void sets_win_min(String value){
		m_s_win_min = value;
	}

	String gets_win_0(){
		return m_s_win_0;
	}

	void sets_win_0(String value){
		m_s_win_0 = value;
	}

	String gets_cwin_max(){
		return m_s_cwin_max;
	}

	void sets_cwin_max(String value){
		m_s_cwin_max = value;
	}

	String gets_cwin_min(){
		return m_s_cwin_min;
	}

	void sets_cwin_min(String value){
		m_s_cwin_min = value;
	}

	String gets_cwin_ini(){
		return m_s_cwin_ini;
	}

	void sets_cwin_ini(String value){
		m_s_cwin_ini = value;
	}

	String gets_rtt_avg(){
		return m_s_rtt_avg;
	}

	void sets_rtt_avg(String value){
		m_s_rtt_avg = value;
	}

	String gets_rtt_min(){
		return m_s_rtt_min;
	}

	void sets_rtt_min(String value){
		m_s_rtt_min = value;
	}

	String gets_rtt_max(){
		return m_s_rtt_max;
	}

	void sets_rtt_max(String value){
		m_s_rtt_max = value;
	}

	String gets_rtt_std(){
		return m_s_rtt_std;
	}

	void sets_rtt_std(String value){
		m_s_rtt_std = value;
	}

	String gets_rtt_cnt(){
		return m_s_rtt_cnt;
	}

	void sets_rtt_cnt(String value){
		m_s_rtt_cnt = value;
	}

	String gets_ttl_min(){
		return m_s_ttl_min;
	}

	void sets_ttl_min(String value){
		m_s_ttl_min = value;
	}

	String gets_ttl_max(){
		return m_s_ttl_max;
	}

	void sets_ttl_max(String value){
		m_s_ttl_max = value;
	}

	String gets_pkts_rto(){
		return m_s_pkts_rto;
	}

	void sets_pkts_rto(String value){
		m_s_pkts_rto = value;
	}

	String gets_pkts_fs(){
		return m_s_pkts_fs;
	}

	void sets_pkts_fs(String value){
		m_s_pkts_fs = value;
	}

	String gets_pkts_reor(){
		return m_s_pkts_reor;
	}

	void sets_pkts_reor(String value){
		m_s_pkts_reor = value;
	}

	String gets_pkts_dup(){
		return m_s_pkts_dup;
	}

	void sets_pkts_dup(String value){
		m_s_pkts_dup = value;
	}

	String gets_pkts_unk(){
		return m_s_pkts_unk;
	}

	void sets_pkts_unk(String value){
		m_s_pkts_unk = value;
	}

	String gets_pkts_fc(){
		return m_s_pkts_fc;
	}

	void sets_pkts_fc(String value){
		m_s_pkts_fc = value;
	}

	String gets_pkts_unrto(){
		return m_s_pkts_unrto;
	}

	void sets_pkts_unrto(String value){
		m_s_pkts_unrto = value;
	}

	String gets_pkts_unfs(){
		return m_s_pkts_unfs;
	}

	void sets_pkts_unfs(String value){
		m_s_pkts_unfs = value;
	}

	String gets_syn_retx(){
		return m_s_syn_retx;
	}

	void sets_syn_retx(String value){
		m_s_syn_retx = value;
	}

	String getdurat(){
		return m_durat;
	}

	void setdurat(String value){
		m_durat = value;
	}

	String getfirst(){
		return m_first;
	}

	void setfirst(String value){
		m_first = value;
	}

	String getlast(){
		return m_last;
	}

	void setlast(String value){
		m_last = value;
	}

	String getc_first(){
		return m_c_first;
	}

	void setc_first(String value){
		m_c_first = value;
	}

	String gets_first(){
		return m_s_first;
	}

	void sets_first(String value){
		m_s_first = value;
	}

	String getc_last(){
		return m_c_last;
	}

	void setc_last(String value){
		m_c_last = value;
	}

	String gets_last(){
		return m_s_last;
	}

	void sets_last(String value){
		m_s_last = value;
	}

	String getc_first_ack(){
		return m_c_first_ack;
	}

	void setc_first_ack(String value){
		m_c_first_ack = value;
	}

	String gets_first_ack(){
		return m_s_first_ack;
	}

	void sets_first_ack(String value){
		m_s_first_ack = value;
	}

	String getfirst_abs(){
		return m_first_abs;
	}

	void setfirst_abs(String value){
		m_first_abs = value;
	}

	String getc_isint(){
		return m_c_isint;
	}

	void setc_isint(String value){
		m_c_isint = value;
	}

	String gets_isint(){
		return m_s_isint;
	}

	void sets_isint(String value){
		m_s_isint = value;
	}

	String getcon_t(){
		return m_con_t;
	}

	void setcon_t(String value){
		m_con_t = value;
	}

	String getp2p_t(){
		return m_p2p_t;
	}

	void setp2p_t(String value){
		m_p2p_t = value;
	}

	String getp2p_st(){
		return m_p2p_st;
	}

	void setp2p_st(String value){
		m_p2p_st = value;
	}

	String geted2k_data(){
		return m_ed2k_data;
	}

	void seted2k_data(String value){
		m_ed2k_data = value;
	}

	String geted2k_sig(){
		return m_ed2k_sig;
	}

	void seted2k_sig(String value){
		m_ed2k_sig = value;
	}

	String geted2k_c2s(){
		return m_ed2k_c2s;
	}

	void seted2k_c2s(String value){
		m_ed2k_c2s = value;
	}

	String geted2k_c2c(){
		return m_ed2k_c2c;
	}

	void seted2k_c2c(String value){
		m_ed2k_c2c = value;
	}

	String geted2k_chat(){
		return m_ed2k_chat;
	}

	void seted2k_chat(String value){
		m_ed2k_chat = value;
	}

	String gethttp_t(){
		return m_http_t;
	}

	void sethttp_t(String value){
		m_http_t = value;
	}

	String getc_pkts_push(){
		return m_c_pkts_push;
	}

	void setc_pkts_push(String value){
		m_c_pkts_push = value;
	}

	String gets_pkts_push(){
		return m_s_pkts_push;
	}

	void sets_pkts_push(String value){
		m_s_pkts_push = value;
	}

	String getc_ssl(){
		return m_c_ssl;
	}

	void setc_ssl(String value){
		m_c_ssl = value;
	}

	String gets_ssl(){
		return m_s_ssl;
	}

	void sets_ssl(String value){
		m_s_ssl = value;
	}
}