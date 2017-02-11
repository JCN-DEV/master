'use strict';

angular.module('stepApp')
    .factory('AlmAttendanceStatusSearch', function ($resource) {
        return $resource('api/_search/almAttendanceStatuss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
