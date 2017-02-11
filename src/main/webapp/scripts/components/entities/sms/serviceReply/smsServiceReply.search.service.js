'use strict';

angular.module('stepApp')
    .factory('SmsServiceReplySearch', function ($resource) {
        return $resource('api/_search/smsServiceReplys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('SmsServiceComplaintReplySearch', function ($resource) {
        return $resource('api/smsServiceReplys/complaint/:id', {}, {
            'query': { method: 'GET', isArray: true}
        })
    })
    .factory('SmsServiceReplySearchByDepartment', function ($resource) {
        return $resource('api/smsServiceReplys/department/:id', {}, {
            'query': { method: 'GET', isArray: true}
        })
    });
