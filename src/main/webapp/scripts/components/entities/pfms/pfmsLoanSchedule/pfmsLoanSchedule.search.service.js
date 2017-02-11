'use strict';

angular.module('stepApp')
    .factory('PfmsLoanScheduleSearch', function ($resource) {
        return $resource('api/_search/pfmsLoanSchedules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
