'use strict';

describe('MpoCommitteePersonInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoCommitteePersonInfo, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoCommitteePersonInfo = jasmine.createSpy('MockMpoCommitteePersonInfo');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoCommitteePersonInfo': MockMpoCommitteePersonInfo,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("MpoCommitteePersonInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoCommitteePersonInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
