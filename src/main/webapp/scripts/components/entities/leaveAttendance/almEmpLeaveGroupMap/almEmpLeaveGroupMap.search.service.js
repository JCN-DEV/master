'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveGroupMapSearch', function ($resource) {
        return $resource('api/_search/almEmpLeaveGroupMaps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
