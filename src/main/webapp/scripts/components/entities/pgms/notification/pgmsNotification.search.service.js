'use strict';

angular.module('stepApp')
    .factory('PgmsNotificationSearch', function ($resource) {
        return $resource('api/_search/pgmsNotifications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
