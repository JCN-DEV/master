'use strict';

angular.module('stepApp')
    .factory('AlmOnDutyLeaveAppSearch', function ($resource) {
        return $resource('api/_search/almOnDutyLeaveApps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
