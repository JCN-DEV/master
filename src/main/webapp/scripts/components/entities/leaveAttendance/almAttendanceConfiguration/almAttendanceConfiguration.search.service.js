'use strict';

angular.module('stepApp')
    .factory('AlmAttendanceConfigurationSearch', function ($resource) {
        return $resource('api/_search/almAttendanceConfigurations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
