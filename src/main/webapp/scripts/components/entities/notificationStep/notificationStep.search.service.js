'use strict';

angular.module('stepApp')
    .factory('NotificationStepSearch', function ($resource) {
        return $resource('api/_search/notificationSteps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
