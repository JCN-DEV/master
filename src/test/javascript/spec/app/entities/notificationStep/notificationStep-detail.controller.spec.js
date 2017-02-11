'use strict';

describe('NotificationStep Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNotificationStep;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNotificationStep = jasmine.createSpy('MockNotificationStep');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'NotificationStep': MockNotificationStep
        };
        createController = function() {
            $injector.get('$controller')("NotificationStepDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:notificationStepUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
