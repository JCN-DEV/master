'use strict';

angular.module('stepApp')
    .factory('SmsServiceComplaintSearch', function ($resource) {
        return $resource('api/_search/smsServiceComplaints/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
