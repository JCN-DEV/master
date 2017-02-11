'use strict';

describe('SmsServiceType Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceType;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceType = jasmine.createSpy('MockSmsServiceType');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceType': MockSmsServiceType
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceTypeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceTypeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
