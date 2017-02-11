'use strict';

angular.module('stepApp')
    .factory('MpoCommitteeHistorySearch', function ($resource) {
        return $resource('api/_search/mpoCommitteeHistorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoCommitteeHistoryMonthYear', function ($resource) {
        return $resource('api/mpoCommitteeHistorys/search/:month/:year', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoCommitteeHistoryOneByEmailMonthYear', function ($resource) {
        return $resource('api/mpoCommitteeHistorys/user/:email/:month/:year', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
