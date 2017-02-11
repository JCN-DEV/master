'use strict';

angular.module('stepApp')
    .factory('AlmAttendanceInformationSearch', function ($resource) {
        return $resource('api/_search/almAttendanceInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
