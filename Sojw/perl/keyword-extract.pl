

#!/usr/local/bin/perl

# load LWP library:
use LWP::UserAgent;
use HTML::Parse;
use Encode;
use utf8;

#binmode(STDOUT, ':encoding(utf8)');
#binmode(STDIN, ':encoding(utf8)');

# define a URL
my $url = 'http://www.naver.com';

# create UserAgent object
my $ua = new LWP::UserAgent;

# set a user agent (browser-id)
# $ua->agent('Mozilla/5.5 (compatible; MSIE 5.5; Windows NT 5.1)');

# timeout:
$ua->timeout(15);

# proceed the request:
my $request = HTTP::Request->new('GET');
$request->url($url);
#$request->header(Content_Type => 'text/html; version=3.2', content_language => 'ko.kr');

my $response = $ua->request($request);

#
# responses:
#

# response code (like 200, 404, etc)
my $code = $response->code;

# headers (Server: Apache, Content-Type: text/html, ...)
my $headers = $response->headers_as_string;

# HTML body:
my $body =  $response->content;
#print $body;
#my $encode_body = decode('utf8', $response->content);
#print encode('utf8', $encode_body);

# print the website content:
# print $body;


# do some parsing:


#my $parsed_html = HTML::Parse::parse_html($body);
#for (@{ $parsed_html->extract_links(qw(a body img)) }) {
#
#    # extract all links (a, body, img)
#    my ($link) = @$_;
#
#    # print link:
#    print encode('utf8', $link) . "\n";
#}



my $parsed_html = HTML::Parse::parse_html(decode('utf8', $body));


my $realrank_list1 = $parsed_html->find_by_attribute('id', 'realrank');
#print $realrank_list1->as_text();
#print encode('utf8', $realrank_list1->as_text()) . "\n\n";

my @content_list = $realrank_list1->content_list();

#print @content_list;

my $listCount = @content_list;

if($listCount < 1) {
	return;
}

my ($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $isdst) = localtime;

open INPUT, ">keyword-" . ($year + 1900) . ($mon + 1) . $mday . $hour . $min . ".txt" or die "File Open Error:$!";

foreach $node ( @content_list ) {
  if (defined $node && $node ne "") {
    my $isLastRank = $node->attr("id");
    if(!$isLastRank) {
      #print "element : " . $node . "\n";
      print "node : " . encode('utf8', $node->as_text()) . "\n";
  	  print "value : " . $node->attr('value') . "\n";
  	  print INPUT $node->attr('value') . "/" . encode('utf8', $node->as_text()) . "\n";
    }
  }
}

close INPUT;

#my @realrank_list = $parsed_html->look_down(
#    _tag => "ol",
#    sub { $_[0]->attr('id') eq "realrank" }
#  );
#
#
#my $keywordCount = 1;
#foreach $x ( @realrank_list ) {
#
#	print encode('utf8', $x->as_text()) .  " = " . $keywordCount++ . "\n\n";
#}
#
