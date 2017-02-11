'use strict';

angular.module('stepApp')
    .factory('AlmHolidaySearch', function ($resource) {
        return $resource('api/_search/almHolidays/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
